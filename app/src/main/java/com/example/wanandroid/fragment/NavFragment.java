package com.example.wanandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;


public class NavFragment extends Fragment {
    private static final String KEY_MSG = "msg";
    private String msg;


    public NavFragment() {

    }

    public static NavFragment newInstance(String msg) {
        NavFragment fragment = new NavFragment();
        Bundle args = new Bundle();
        args.putString(KEY_MSG, msg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msg = getArguments().getString(KEY_MSG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav, container, false);
        return view;
    }
}
