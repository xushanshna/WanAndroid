package com.example.wanandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wanandroid.R;


public class WeChatFragment extends Fragment {
    private static final String KEY_MSG = "msg";
    private String msg;

    public WeChatFragment() {
    }

    public static WeChatFragment newInstance(String param1) {
        WeChatFragment fragment = new WeChatFragment();
        Bundle args = new Bundle();
        args.putString(KEY_MSG, param1);
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wechat, container, false);
    }
}