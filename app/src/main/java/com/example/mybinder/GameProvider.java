package com.example.mybinder;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class GameProvider extends ContentProvider {
    public static final String AUTHORITY = "com.example.mybinder.GameProvider";
    public static final Uri GAME_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/game");
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private SQLiteDatabase mDb;
    private Context context;
    private String table;
    static {mUriMatcher.addURI(AUTHORITY, " game", 0);}
    @Override
    public boolean onCreate() {
        table = DbOpenHelper.GAME_TABLE_NAME;
        context = getContext();
        initProvider();
        return false;
    }

    private void initProvider(){
        mDb = new DbOpenHelper(context).getWritableDatabase();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDb.execSQL("delete from " + DbOpenHelper.GAME_TABLE_NAME);
                mDb.execSQL("insert into game values(1, '九阴真经ol','最好玩的武侠网游');");
            }
        }).start();
    }
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String table = DbOpenHelper.GAME_TABLE_NAME;
        Cursor cursor = mDb.query(table, projection, selection, selectionArgs, null, sortOrder, null);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        mDb.insert(table, null, values);
        context.getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
