package com.cs496.macaron_together_admin;

import android.content.Context;
import android.content.Intent;
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

import java.io.Serializable;
import java.util.List;

/**
 * Created by q on 2017-01-03.
 */

public class BoardViewAdapter extends RecyclerView.Adapter<BoardViewAdapter.ViewHolder> {
    private DatabaseReference events;
    private List<PostData> posts_list;
    private Context context;
    private Fragment f;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public TextView mTextView2;
        public CardView card;

        public ViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.d_title);
            mTextView2 = (TextView) view.findViewById(R.id.d_author);
            card = (CardView) view.findViewById(R.id.post_item);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BoardViewAdapter(FragmentActivity activity, Fragment f, List<PostData> list) {
        this.context = activity;
        this.f = f;
        this.posts_list = list;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BoardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        final PostData post = posts_list.get(position);
        holder.mTextView.setText(post.getTitle());
        holder.mTextView2.setText(post.getAuthor());
        holder.mTextView.setTypeface(App.myFont);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostView.class);
                intent.putExtra("post", (Serializable) post);
                context.startActivity(intent);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return posts_list.size();
    }

}

