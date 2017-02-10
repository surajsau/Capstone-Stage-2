package com.halfplatepoha.telemprompter.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by surajkumarsau on 10/02/17.
 */

public class DataProvider extends ContentProvider {

    private DbHelper dbHelper;
    public static UriMatcher uriMatcher;
    public static final int FILEINFOS = 1;
    public static final int FILEINFO = 2;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.FileInfoColumns.TABLE_NAME, FILEINFOS);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.FileInfoColumns.TABLE_NAME + "/#", FILEINFO);
    }

    @Override
    public boolean onCreate() {
        dbHelper = DbHelper.getInstance(getContext(), "TeleprompDb", null, 1);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case FILEINFOS:
                return Contract.FileInfoColumns.CONTENT_TYPE;
            case FILEINFO:
                return Contract.FileInfoColumns.CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case FILEINFOS:{
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                long id = db.insert(Contract.FileInfoColumns.TABLE_NAME, null, values);
                return getUriForId(id, uri);
            }

            default:
                throw new IllegalArgumentException("Please use valid uri " + uri.toString());
        }
    }

    private Uri getUriForId(long id, Uri uri) {
        if(id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver()
                    .notifyChange(itemUri, null);
            return itemUri;
        }
        throw new SQLException(
                "Problem while inserting into uri: " + uri);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case FILEINFOS: {
                delCount = sqLiteDatabase.delete(Contract.FileInfoColumns.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case FILEINFO: {
                String where = Contract.FileInfoColumns._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    where += " AND " + selection;
                delCount = sqLiteDatabase.delete(Contract.FileInfoColumns.TABLE_NAME, where, selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid uri to delete");
        }
        if (delCount > 0) getContext().getContentResolver().notifyChange(uri, null);
        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int updateCount = 0;
        switch (uriMatcher.match(uri)) {
            case FILEINFOS: {
                updateCount = sqLiteDatabase.update(Contract.FileInfoColumns.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case FILEINFO: {
                String where = Contract.FileInfoColumns._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection))
                    where += " AND " + selection;
                updateCount = sqLiteDatabase.update(Contract.FileInfoColumns.TABLE_NAME, values, where, selectionArgs);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid uri to update");
        }
        if (updateCount > 0) getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

    public static final class Contract {
        static final String AUTHORITY = "com.halfplatepoha.telemprompter";
        static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

        public static class FileInfoColumns implements BaseColumns {
            public static final String TABLE_NAME = "fileInfos";
            public static final String FILE_NAME = "file_name";
            public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + TABLE_NAME;
            public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/fileInfo";
            public static final String FILE_PATH = "address";
            public static final Uri CONTENT_URI = Uri.withAppendedPath(Contract.CONTENT_URI, TABLE_NAME);
        }
    }
}
