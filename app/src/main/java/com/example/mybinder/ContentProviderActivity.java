package com.example.mybinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class ContentProviderActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        Uri uri = Uri.parse("content://com.example.mybinder.GameProvider");
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", 2);
        contentValues.put("name","大航海时代");
        contentValues.put("describe","最好玩的航海网游");
        getContentResolver().insert(uri, contentValues);
        Cursor gameCursor = getContentResolver().query(uri, new String[]{"name", "describe"},null,null,null);
        while (gameCursor.moveToNext()){
            Game mGame = new Game(gameCursor.getString(0), gameCursor.getString(1));
            Log.i(TAG, mGame.gameName + "---" + mGame.gameDescribe);
        }

    }
}