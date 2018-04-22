package com.example.android.simplefeeder;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>
{
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private View.OnClickListener mClickListner;
    private ToggleButton toggle;
    List<Details> detailValues;
    Context context;

    public AdapterClass(List<Details> detailValues,Context context) {
        this.detailValues = detailValues;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent,final int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final Details details=detailValues.get(position);
        holder.title.setText(details.getTitle());
        holder.description.setText(details.getDescription());
        holder.date.setText(details.getDate());
        Picasso.with(context).load(details.getImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return detailValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;
        private TextView description;
        private ImageView image;
        private TextView date;

        public ViewHolder(final View itemView) {
            super(itemView);
            helper=new DatabaseHelper(context);
             db=helper.getWritableDatabase();
             Log.d("db","involking writable database");
            final ContentValues values=new ContentValues();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    Intent i=new Intent(context,WebActivity.class);
                    i.putExtra("WEB_ADDRESS",detailValues.get(pos).getLink());
                    context.startActivity(i);

                }
            });
            toggle=(ToggleButton)itemView.findViewById(R.id.toggle_button);
            Log.d("db","context is "+context);
            if(context instanceof BookmarkActivity)
            {
                Log.d("db","within condition"+context);
                toggle.setVisibility(View.INVISIBLE);
            }
            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos=getAdapterPosition();
                    if(isChecked)
                    {
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_TITLE,detailValues.get(pos).getTitle());
                        Log.d("db",detailValues.get(pos).getTitle());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_DESCRIPTION,detailValues.get(pos).getDescription());
                        Log.d("db",detailValues.get(pos).getDescription());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_IMAGEURL,detailValues.get(pos).getImage());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_URL,detailValues.get(pos).getLink());
                        values.put(DatabaseContract.DataBaseEntries.COLUMN_DATE,detailValues.get(pos).getDate());
                        long newRow=db.insert(DatabaseContract.DataBaseEntries.TABLE_NAME,null,values);
                        Log.d("db",""+newRow);
                        Toast.makeText(context,"i am on now",Toast.LENGTH_SHORT).show();
                    }

                    else
                    {
                     //   String temp=detailValues.get(pos).getTitle();
                     //   int k=db.delete(DatabaseContract.DataBaseEntries.TABLE_NAME,
                     //           DatabaseContract.DataBaseEntries.COLUMN_TITLE +"="+temp,null);
                      // Log.d("db","deleted rows are "+k);
                        Toast.makeText(context,"i am off again now",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            title=(TextView)itemView.findViewById(R.id.heading);
            description=(TextView)itemView.findViewById(R.id.description);
            image=(ImageView)itemView.findViewById(R.id.imageView);
            date=(TextView) itemView.findViewById(R.id.date);
        }
    }
}

