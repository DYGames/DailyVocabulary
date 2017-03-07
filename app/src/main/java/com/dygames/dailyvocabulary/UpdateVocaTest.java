package com.dygames.dailyvocabulary;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.util.Timer;
import java.util.TimerTask;

public class UpdateVocaTest extends Service {
    private static Timer timer = new Timer();
    private static UpdateVocaTest updatevocatest;

    @Override
    public void onStart(Intent intent, int startId){
        updatevocatest = this;
        timer.scheduleAtFixedRate(new mainTask(), 0, 250);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            RemoteViews updateViews = new RemoteViews(updatevocatest.getPackageName(), R.layout.voca_test);

            for (String text : AppData.getInstance().setTextMap.keySet())
            {
                updateViews.setTextViewText(AppData.getInstance().setTextMap.get(text), text);
            }

            AppData.getInstance().setTextMap.clear();

            ComponentName componentname = new ComponentName(updatevocatest, VocaTest.class);
            AppWidgetManager appwidgetmanager = AppWidgetManager.getInstance(updatevocatest);
            appwidgetmanager.updateAppWidget(componentname, updateViews);


        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}