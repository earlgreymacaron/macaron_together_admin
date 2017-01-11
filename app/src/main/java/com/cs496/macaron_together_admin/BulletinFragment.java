package com.cs496.macaron_together_admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by q on 2017-01-05.
 */

public class BulletinFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private BoardViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<PostData> posts = new ArrayList<PostData>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsDB = database.getReference();
        eventsDB.child("board").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (getView() != null) {loadPosts(getView(),dataSnapshot);}
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bulletin, container, false);


        return view;
    }

    private void loadPosts(View view, DataSnapshot snapshot) {


        String title = snapshot.child("title").getValue().toString();
        String author = snapshot.child("author").getValue().toString();
        String body = snapshot.child("body").getValue().toString();
        String stamp = snapshot.child("timestamp").getValue().toString();

        PostData data = new PostData(title, author, body, stamp);
        posts.add(data);

        //Set data on Recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.board_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mAdapter = new BoardViewAdapter(getActivity(), BulletinFragment.this,posts);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }



}


