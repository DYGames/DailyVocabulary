package com.dygames.dailyvocabulary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by DYGames on 2017. 3. 4..
 */

public class GenerateNewWordTest {
    static int NewWordTest(MainActivity mainActivity, TextView keyword, Button[] buttons)
    {
        Random random = new Random(System.currentTimeMillis());

        Integer answernum = random.nextInt(4);
        try {
            int answerposition;

            SQLiteDatabase database = AppData.getInstance().myhelper.getReadableDatabase();

            Cursor cursor;
            cursor = database.rawQuery("SELECT * FROM groupTBL;", null);

            answerposition = random.nextInt(cursor.getCount());
            cursor.moveToPosition(answerposition);
            keyword.setText(cursor.getString(0));

            List<Integer> examNum = new ArrayList<>(Arrays.asList(0,1,2,3));
            Collections.shuffle(examNum);

            for(int i = 0 ; i < 4; i++)
            {
                cursor.moveToPosition(examNum.get(i) == answernum ? answerposition : random.nextInt(cursor.getCount()));
                buttons[examNum.get(i)].setText(cursor.getString(0));

                WordParseTask wordParseTask = new WordParseTask();
                wordParseTask.delegate = mainActivity;
                wordParseTask.wordTarget = buttons[examNum.get(i)];
                wordParseTask.execute(buttons[examNum.get(i)].getText().toString());

                final String toastmsg = examNum.get(i) == answernum ? "correct" : "incorrect";

                buttons[examNum.get(i)].setOnClickListener(new View.OnClickListener()
                {
                    public void onClick(View arg0)
                    {
                        //Toast.makeText(context, toastmsg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        catch (Exception e){
            keyword.setText(e.toString());
        }

        return answernum;
    }

    static int NewWordTestForWidget(RemoteViews views, int keyword, int[] buttons)
    {
        Random random = new Random(System.currentTimeMillis());

        Integer answernum = random.nextInt(4);
        try {
            int answerposition;

            SQLiteDatabase database = AppData.getInstance().myhelper.getReadableDatabase();

            Cursor cursor;
            cursor = database.rawQuery("SELECT * FROM groupTBL;", null);

            answerposition = random.nextInt(cursor.getCount());
            cursor.moveToPosition(answerposition);

            views.setTextViewText(keyword,cursor.getString(0));

            List<Integer> examNum = new ArrayList<>(Arrays.asList(0,1,2,3));
            Collections.shuffle(examNum);

            for(int i = 0 ; i < 4; i++)
            {
                cursor.moveToPosition(examNum.get(i) == answernum ? answerposition : random.nextInt(cursor.getCount()));
                views.setTextViewText(buttons[examNum.get(i)], cursor.getString(0));

                WordParseTask wordParseTask = new WordParseTask();
                wordParseTask.delegate = null;
                wordParseTask.wordTargetId = buttons[examNum.get(i)];
                wordParseTask.execute(cursor.getString(0));

              //  final String toastmsg = examNum.get(i) == answernum ? "correct" : "incorrect";

              //  buttons[examNum.get(i)].setOnClickListener(new View.OnClickListener()
              //  {
              //      public void onClick(View arg0)
              //      {
              //          //Toast.makeText(context, toastmsg, Toast.LENGTH_SHORT).show();
              //      }
              //  });
            }
        }
        catch (Exception e){
            views.setTextViewText(keyword, e.toString());
        }

        return answernum;
    }

}
