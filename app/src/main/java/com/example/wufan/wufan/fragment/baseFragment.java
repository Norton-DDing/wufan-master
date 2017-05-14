package com.example.wufan.wufan.fragment;

/**
 * Created by larry on 2017/5/14.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

/**
 * Created by chenlongfei on 2016/12/1.
 */

public abstract class baseFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViews();
    }

    protected abstract void initViews();

    protected abstract int getContentViewResId();
}

