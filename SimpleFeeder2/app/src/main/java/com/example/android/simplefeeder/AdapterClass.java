package com.example.android.simplefeeder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.attr.start;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static java.security.AccessController.getContext;

/**
 * Created by lenovo on 4/8/2018.
 */

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {

    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private ToggleButton toggle1;
    List<Details> detailValues;
    Context context;

    public AdapterClass(List<Details> detailValues, Context context) {
        this.detailValues = detailValues;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Details details = detailValues.get(position);

        holder.title.setText(details.getTitle());
        holder.description.setText(details.getDescription());
        holder.date.setText(details.getDate());
        Picasso.with(context).load(details.getImage()).into(holder.image);
            Log.d("abc","pos "+position);
            Log.d("abc ","title "+details.getTitle());
            Log.d("abc ","value returned "+fix(holder.findState(details.getTitle())));
            toggle1.setChecked(fix(holder.findState(details.getTitle())));


    }
    boolean fix(int a)
    {
        if(a==1)
            return  true;
        return false;
    }

    @Override
    public int getItemCount() {
        return detailValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private ImageView image;
        private TextView date;


        public ViewHolder(View itemView) {
            super(itemView);
            this.setIsRecyclable(false);
         //  Log.d("db", "beginning "+getAdapterPosition());
            helper = new DatabaseHelper(context);
            db = helper.getWritableDatabase();
            final ContentValues values = new ContentValues();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent i = new Intent(context, WebActivity.class);
                    i.putExtra("WEB_ADDRESS", detailValues.get(pos).getLink());
                    context.startActivity(i);

                }
            });
           final ToggleButton toggle = (ToggleButton) itemView.findViewById(R.id.toggle_button);
           toggle1=toggle;
           // toggle1=(ToggleButton)itemView.findViewById(R.id.toggle_button);
            if (context instanceof BookmarkActivity) {
                toggle.setVisibility(View.INVISIBLE);
            }
            toggle.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View v) {

                    int pos=getAdapterPosition();
                    if(toggle.isChecked()) {
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_TITLE, detailValues.get(pos).getTitle());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_DESCRIPTION, detailValues.get(pos).getDescription());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_IMAGEURL, detailValues.get(pos).getImage());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_URL, detailValues.get(pos).getLink());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_DATE, detailValues.get(pos).getDate());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_STATE,1);
                        long newRow = db.insert(DatabaseContract.DataBaseEntries.TABLE_NAME, null, values);
                        Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String temp[]=new String[1];
                        temp[0]=detailValues.get(pos).getTitle();
                        int k=db.delete(DatabaseContract.DataBaseEntries.TABLE_NAME,DatabaseContract.DataBaseEntries
                        .COLUMN_TITLE+" =?",temp);
                        Toast.makeText(context, "Removed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
           /*toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos=getAdapterPosition();
                    Log.d("db", "here yoooo" +pos+ " ");
                    if (isChecked) {
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_TITLE, detailValues.get(pos).getTitle());
                        Log.d("db", detailValues.get(pos).getTitle());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_DESCRIPTION, detailValues.get(pos).getDescription());
                        Log.d("db", detailValues.get(pos).getDescription());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_IMAGEURL, detailValues.get(pos).getImage());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_URL, detailValues.get(pos).getLink());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_DATE, detailValues.get(pos).getDate());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_STATE,1);
                        long newRow = db.insert(DatabaseContract.DataBaseEntries.TABLE_NAME, null, values);
                        Log.d("db", "" + newRow);
                       // Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show();
                    } else {

                        //   String temp=detailValues.get(pos).getTitle();
                        //   int k=db.delete(DatabaseContract.DataBaseEntries.TABLE_NAME,
                        //           DatabaseContract.DataBaseEntries.COLUMN_TITLE +"="+temp,null);
                        // Log.d("db","deleted rows are "+k);
                       // Toast.makeText(context, "Removed ", Toast.LENGTH_SHORT).show();
                    }

                }
            });*/
            title = (TextView) itemView.findViewById(R.id.heading);
            description = (TextView) itemView.findViewById(R.id.description);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            date = (TextView) itemView.findViewById(R.id.date);

        }
        public int findState(String temp)
        {

            DatabaseHelper helper=new DatabaseHelper(context);
            SQLiteDatabase db=helper.getReadableDatabase();
            String s[]=new String[1];
            s[0]=temp;
            String column[]={DatabaseContract.DataBaseEntries.COLUMN_TITLE};
            String selection=DatabaseContract.DataBaseEntries.COLUMN_TITLE + "=?";
            Cursor cursor = db.query(DatabaseContract.DataBaseEntries.TABLE_NAME, column, selection,
                    s, null, null, null);

            int t;
            if (cursor != null && cursor.moveToFirst()) {
                t = 1;
                return 1;
            } else {
                t = 0;
                return 0;
            }
        }


    }
}

