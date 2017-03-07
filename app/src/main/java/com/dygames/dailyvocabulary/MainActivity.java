package com.dygames.dailyvocabulary;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Debug;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView Keyword;
    Button[] buttons;

    class GetWordListTask extends  AsyncTask<Void, Void, Void>
    {
        private Exception exception;
        public MainActivity delegate;


        protected Void doInBackground(Void... pra) {
            try {
                SQLiteDatabase database;
                database = AppData.getInstance().myhelper.getWritableDatabase();

                String LastString = "";

                Integer page = 1;
                Integer level = 1;
                Document dom = Jsoup.connect("http://endic.naver.com/rank.nhn?sLn=kr&pubLev=" + level.toString() + "&firstWord=all&posp=all&pageNo="+ page.toString()).get();
                Elements elm = dom.select("#content > div.nbox_02 > div.title.entrylist > table > tbody > tr > td.f_name > div > a > span");
                do {
                    for(int i = 0; i < elm.size(); i++){
                        Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z]+$");
                        Matcher m = p.matcher(elm.get(i).text());
                        if(elm.get(i) != null) {
                            if (m.find() && !LastString.equals(elm.get(i).text())) {
                                database.execSQL("INSERT INTO groupTBL VALUES ( '" + elm.get(i).text() + "', " + page.toString() + " );");
                                LastString = elm.get(i).text();
                            }
                        }
                    }
                    page++;
                    dom = Jsoup.connect("http://endic.naver.com/rank.nhn?sLn=kr&pubLev=" + level.toString() + "&firstWord=all&posp=all&pageNo="+ page.toString()).get();
                    elm = dom.select("#content > div.nbox_02 > div.title.entrylist > table > tbody > tr > td.f_name > div > a > span");
                    Log.d("page", page.toString());
                }while (page < 434);
                database.close();
                return null;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        protected void onPostExecute(Elements result) {
            Log.d("end","endgetwordlist");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppData.getInstance().myhelper = new myDBHelper(this);

        //GetWordListTask getWordListTask = new GetWordListTask();
        //getWordListTask.delegate = this;
        //getWordListTask.execute();

        buttons = new Button[4];

        Keyword = (TextView) findViewById(R.id.Keyword);

        buttons[0] = (Button) findViewById(R.id.Mean1);
        buttons[1] = (Button) findViewById(R.id.Mean2);
        buttons[2] = (Button) findViewById(R.id.Mean3);
        buttons[3] = (Button) findViewById(R.id.Mean4);

        //GenerateNewWordTest.NewWordTest(this, Keyword, buttons);

        final MainActivity mainActivity = this;

        ((Button)findViewById(R.id.ResetWord)).setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0)
            {
                GenerateNewWordTest.NewWordTest(mainActivity, Keyword, buttons);
            }
        });
    }

    public void postResult(Button wordTarget, Elements elements)
    {
        if(elements.size() > 0)
            wordTarget.setText(elements.get(0).text());
    }
}
