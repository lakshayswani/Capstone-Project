package com.lakshayswani.virtuastock.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.lakshayswani.virtuastock.R;
import com.lakshayswani.virtuastock.ui.Dashboard;

/**
 * Created by lenovo on 20-12-2016.
 */
public class MyStocksActivityWidget extends AppWidgetProvider {

    /**
     * Update app widget.
     *
     * @param context          the context
     * @param appWidgetManager the app widget manager
     * @param appWidgetId      the app widget id
     */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widget_balance, context.getResources().getString(R.string.app_name));
        views.setRemoteAdapter(R.id.widget_stock_list, new Intent(context, WidgetRemoteViewsFactory.class));

        Intent appIntent = new Intent(context, Dashboard.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, 0);

        views.setOnClickPendingIntent(R.id.widget_balance, appPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
