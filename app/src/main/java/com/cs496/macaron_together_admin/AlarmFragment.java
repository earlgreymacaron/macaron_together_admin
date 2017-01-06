package com.cs496.macaron_together_admin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by q on 2017-01-05.
 */

public class AlarmFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private AlarmViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        loadAlarms(view);
        return view;
    }

    private void loadAlarms(View view) {
        JSONArray alarms = new JSONArray();


        //Get data from Firebase
        try {
            //   String json = eventsDB.get();
            //  events = new JSONArray(json);
            JSONObject alarm = new JSONObject();
            alarm.put("msg", "박예슬님이 7500원을 입금하였습니다.");
            alarms.put(alarm);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Set data on Recyclerview
        mRecyclerView = (RecyclerView) view.findViewById(R.id.alarm_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(0);
        mAdapter = new AlarmViewAdapter(getActivity(), AlarmFragment.this, alarms);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
