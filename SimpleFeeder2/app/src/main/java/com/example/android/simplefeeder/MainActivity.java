package com.example.android.simplefeeder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.android.simplefeeder.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.button;
import static android.R.attr.description;
import static android.R.attr.name;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class MainActivity extends AppCompatActivity {
    private String variable;
    private android.support.v7.widget.Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private TextView display_url;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    public List<Details> detailValues;
    RecyclerView.LayoutManager mLayout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         ActionBar bar=getSupportActionBar();
         bar.setDisplayHomeAsUpEnabled(true);
         bar.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView mNavigationView=(NavigationView)findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                Intent intent;
                switch(id)
                {
                    case R.id.Home:
                        intent=new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                       // Toast.makeText(MainActivity.this,"home",Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        break;
                    case R.id.bookmark:
                        intent=new Intent(MainActivity.this,BookmarkActivity.class);
                        startActivity(intent);
                      //  Toast.makeText(MainActivity.this,"bookmark",Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        break;
                    case R.id.about:
                        intent=new Intent(MainActivity.this,AboutActivity.class);
                        startActivity(intent);
                        //Toast.makeText(MainActivity.this,"about",Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        break;
                    case R.id.help:
                        intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.setData(Uri.parse("sonamazad98@gmail.com"));
                        intent.putExtra(Intent.EXTRA_SUBJECT,"Have a query , need some help . write it here .");
                        intent.putExtra(Intent.EXTRA_EMAIL,"sonamazad98@gmail.com");
                        intent.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivityGmail");

                        startActivity(Intent.createChooser(intent,"sending mail.."));
                        item.setChecked(true);
                        break;
                }
                mDrawerLayout.closeDrawers();
                item.setChecked(false);
                return true;
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        detailValues=new ArrayList<>();
         mLayout=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayout);
        new asynTask().execute();

        //display_url.setText(variable);



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public class asynTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);
            Log.d("result", "on post execute");
            adapter=new AdapterClass(detailValues,MainActivity.this);
            recyclerView.setAdapter(adapter);



        }

        @Override
        protected Void doInBackground(Void... params) {
            //   https://api.androidhive.info/contacts/
            //   https://newsapi.org/v2/top-headlines?sources=new-scientist&apiKey=6e6c6d58b20d403096b8e023979d9431


            fetchData obj = new fetchData();
          /*  Uri.Builder uri=new Uri.Builder();
            uri.scheme("https").authority("newsapi.org").appendPath("v2")
                    .appendPath("top-headlines").appendQueryParameter("sources","new-scientist")
            .appendQueryParameter("apiKey","6e6c6d58b20d403096b8e023979d9431").build();
            //variable=uri.toString();
            */
            //variable="https://newsapi.org/v2/top-headlines?sources=new-scientist&apiKey=76f922a6d60f4134b05d998a5a74a7f2";
            variable = "http://www.sciencemag.org/rss/news_current.xml";
            Log.d("result", variable);

           try{
               detailValues= obj.makeConnection(variable);
               Log.d("result", "going to return result 3");
           }catch (org.xmlpull.v1.XmlPullParserException e)
           {e.printStackTrace();
               Log.d("result", "caught in main error");}
            Log.d("result", "going to return result null 4");
            return null;
        }

        }

    /**
     * Created by lenovo on 4/8/2018.
     */


}