package com.halfplatepoha.telemprompter.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.screens.home.MainActivity;
import com.halfplatepoha.telemprompter.utils.IConstants;

/**
 * Created by surajkumarsau on 12/02/17.
 */

public class AppWidget extends AppWidgetProvider {
    public static final String LAUNCH_ACTION = "com.halfplatepoha.telemprompter.LAUNCH";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(LAUNCH_ACTION)) {
            Intent activityIntent = new Intent(context, MainActivity.class);
            activityIntent.putExtra(IConstants.RESULT_FILE_PATH, intent.getStringExtra(IConstants.RESULT_FILE_PATH));
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(activityIntent);
        }
        super.onReceive(context, intent);
    }

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent intent=new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews rm=new RemoteViews(context.getPackageName(), R.layout.layout_widget);
        rm.setRemoteAdapter(R.id.widget_list, intent);
        rm.setEmptyView(R.id.widget_list,R.id.empty_view3);

        Intent intent1=new Intent(context, AppWidget.class);
        intent1.setAction(LAUNCH_ACTION);
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent1.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rm.setPendingIntentTemplate(R.id.widget_list, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, rm);
    }

    @Override
    public void onDisabled(Context context) {

    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }
}
