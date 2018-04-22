package com.example.android.simplefeeder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {
private ShareActionProvider mAction;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbarabout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
