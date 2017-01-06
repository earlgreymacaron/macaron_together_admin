package com.cs496.macaron_together_admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by q on 2017-01-05.
 */

public class CurrentFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private EventViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_current, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference eventsDB = database.getReference("events");
        eventsDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadEvents(view, dataSnapshot);
                //dataSnapshot.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        FloatingActionButton add = (FloatingActionButton) view.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEventActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void loadEvents(View view, DataSnapshot snapshot) {
        JSONArray events = new JSONArray();

        // Write a message to the database
        DataSnapshot d = snapshot;

        //Get data from Firebase
        try {
         //   String json = eventsDB.get();
          //  events = new JSONArray(json);
            JSONObject event = new JSONObject();
            event.put("shop_name", "Cafe de Fran");
            event.put("deadline","2016-02-02");
            events.put(event);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Set data on Recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.event_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mAdapter = new EventViewAdapter(getActivity(), CurrentFragment.this, events);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
