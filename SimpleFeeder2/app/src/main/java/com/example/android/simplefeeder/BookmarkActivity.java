package com.example.android.simplefeeder;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class BookmarkActivity extends AppCompatActivity {
    private DatabaseHelper helper;
private RecyclerView mRecycler;
private RecyclerView.Adapter mAdapter;
private RecyclerView.LayoutManager mManager;
private ArrayList<Details> favItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbarfav);
        setSupportActionBar(toolbar);
        favItems=new ArrayList<>();
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecycler=(RecyclerView)findViewById(R.id.recyclerViewBookmark);
        mRecycler.setHasFixedSize(true);
     // ToggleButton toggle=(ToggleButton)findViewById(R.id.toggle_button);
       // toggle.setVisibility(View.INVISIBLE);
        //  reading data from database
        helper=new DatabaseHelper(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        Log.d("db","in bookmark non null cursor above");
        Cursor cursor=db.query(DatabaseContract.DataBaseEntries.TABLE_NAME,null,null,
                null,null,null,null);
        String title,description,image,url,date;
        if(cursor!=null)
        {
            Log.d("db","in bookmark non null cursor");
            cursor.moveToFirst();
            do{
               title= cursor.getString(cursor.getColumnIndex(DatabaseContract.DataBaseEntries.COLUMN_TITLE));
                Log.d("db",title);
                description= cursor.getString(cursor.getColumnIndex(DatabaseContract.DataBaseEntries.COLUMN_DESCRIPTION));
                Log.d("db",description);
                image= cursor.getString(cursor.getColumnIndex(DatabaseContract.DataBaseEntries.COLUMN_IMAGEURL));
                Log.d("db",image);
                url= cursor.getString(cursor.getColumnIndex(DatabaseContract.DataBaseEntries.COLUMN_URL));
                Log.d("db",url);
                date= cursor.getString(cursor.getColumnIndex(DatabaseContract.DataBaseEntries.COLUMN_DATE));
                Log.d("db",date);
                Details obj=new Details(title,description,image,url,date);
                Log.d("db",obj.getTitle());
                favItems.add(obj);
            }while (cursor.moveToNext());
        }
        //

        // settting recyclerview adapter
        mManager=new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
        mAdapter=new AdapterClass(favItems,this);
        mRecycler.setAdapter(mAdapter);
    }
}
