package com.cs496.macaron_together_admin;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by q on 2017-01-03.
 */

public class AlarmViewAdapter extends RecyclerView.Adapter<AlarmViewAdapter.ViewHolder> {
    private DatabaseReference events;
    private List<AlarmData> alarm_list;
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
            mTextView = (TextView) view.findViewById(R.id.alarm_msg);
            mImageView = (ImageView) view.findViewById(R.id.alarmicon);
            card = (CardView) view.findViewById(R.id.alarm_card);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlarmViewAdapter(FragmentActivity activity, Fragment f, List<AlarmData> list) {
        this.context = activity;
        this.f = f;
        this.alarm_list = list;
        //this.events = events;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlarmViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alarm_card, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        AlarmData alarm = alarm_list.get(position);
        if (alarm.getType().contains("diposit")) {
            String info[] = alarm.getContent().replace(",","").split(" ");
            String text = info[3] + " "+ info[4] + "에 " + info[1] + "을 입금하였습니다.";
            holder.mTextView.setText(text);
            holder.mImageView.setImageDrawable(context.getDrawable(R.drawable.ic_diposit));
        } else if (alarm.getType().contains("register")) {
            holder.mTextView.setText(alarm.getContent());
            holder.mImageView.setImageDrawable(context.getDrawable(R.drawable.ic_newperson));
        } else if (alarm.getType().contains("buyer")) {
            holder.mTextView.setText(alarm.getContent());
            holder.mImageView.setImageDrawable(context.getDrawable(R.drawable.ic_cart));

        } else if (alarm.getType().contains("bulletin")) {
            holder.mTextView.setText(alarm.getContent());
            holder.mImageView.setImageDrawable(context.getDrawable(R.drawable.ic_newbulletin));

        }
        holder.mTextView.setTypeface(App.myFont);


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {

        return alarm_list.size();
    }

}

