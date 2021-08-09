package com.example.mybinder.ActService;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.mybinder.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent myServiceIntent = new Intent(MainActivity.this, MyService.class);
        this.startService(myServiceIntent);
    }
}


