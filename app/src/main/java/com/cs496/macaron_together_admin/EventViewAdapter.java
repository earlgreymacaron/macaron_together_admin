package com.cs496.macaron_together_admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by q on 2017-01-03.
 */

public class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.ViewHolder> {
    private DatabaseReference events;
    private JSONArray event_list;
    private Context context;
    private Fragment f;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView;
        public TextView mTextView2;
        public CardView card;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.image);
            mTextView = (TextView) view.findViewById(R.id.textview);
            mTextView2 = (TextView) view.findViewById(R.id.textview2);
            card = (CardView) view.findViewById(R.id.eventcard);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public EventViewAdapter(FragmentActivity activity, Fragment f, JSONArray list) {
        this.context = activity;
        this.f = f;
        this.event_list = list;
        //this.events = events;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public EventViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        try {
            JSONObject event = new JSONObject();
            //event = event_list.getJSONObject(0);
            holder.mTextView.setText(event.getString("shop_name"));
            holder.mTextView2.setText(event.getString("deadline"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
       // return 0;
        return event_list.length();
    }

}

