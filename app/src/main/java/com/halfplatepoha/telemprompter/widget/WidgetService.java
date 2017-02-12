package com.halfplatepoha.telemprompter.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.halfplatepoha.telemprompter.R;
import com.halfplatepoha.telemprompter.db.DataProvider;
import com.halfplatepoha.telemprompter.utils.IConstants;

/**
 * Created by surajkumarsau on 12/02/17.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewFactory(getApplicationContext());
    }

    private class RemoteViewFactory implements RemoteViewsFactory {

        private Context context;
        private Cursor cursor;

        public RemoteViewFactory(Context context) {
            this.context = context;
        }

        @Override
        public void onCreate() {
            cursor=context.getContentResolver().query(
                    DataProvider.Contract.FileInfoColumns.CONTENT_URI,
                    new String[]{DataProvider.Contract.FileInfoColumns.FILE_NAME, DataProvider.Contract.FileInfoColumns.FILE_PATH},
                    null, null, null
            );
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            cursor.moveToPosition(position);
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.row_file_item);
            remoteViews.setTextViewText(R.id.file_name, cursor.getString(cursor.getColumnIndex(DataProvider.Contract.FileInfoColumns.FILE_NAME)));

            Intent intent = new Intent();
            intent.putExtra(IConstants.RESULT_FILE_PATH, cursor.getString(cursor.getColumnIndex(DataProvider.Contract.FileInfoColumns.FILE_PATH)));
            remoteViews.setOnClickFillInIntent(R.id.file_name, intent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
