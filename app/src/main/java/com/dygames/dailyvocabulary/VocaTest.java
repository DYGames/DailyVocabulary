package com.dygames.dailyvocabulary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.RemoteViews;


public class VocaTest extends AppWidgetProvider {

    static  final  String OnClick1 = "android.appwidget.action.OnClick1";
    static  final  String OnClick2 = "android.appwidget.action.OnClick2";
    static  final  String OnClick3 = "android.appwidget.action.OnClick3";
    static  final  String OnClick4 = "android.appwidget.action.OnClick4";
    static  final  String ResetWord = "android.appwidget.action.ResetWord";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.voca_test);

    }


    protected PendingIntent getPendingSelfIntent(Context context, String action, int appWidgetId) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, appWidgetId, intent, 0);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.voca_test);
        Intent intent = new Intent(context, UpdateVocaTest.class);
        context.startService(intent);
        for (int appWidgetId : appWidgetIds) {
            views.setOnClickPendingIntent(R.id.WidgetResetWord, getPendingSelfIntent(context, ResetWord, appWidgetId));
            appWidgetManager.updateAppWidget(appWidgetId, views);

            //updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.voca_test);
        watchWidget = new ComponentName(context, VocaTest.class);

        String action = intent.getAction();

        if (action.equals(ResetWord)){
            int[] words = {R.id.WidgetMean1,R.id.WidgetMean2,R.id.WidgetMean3,R.id.WidgetMean4};
            GenerateNewWordTest.NewWordTestForWidget(remoteViews ,R.id.WidgetKeyword, words);

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);
        }

    };


    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }


}

