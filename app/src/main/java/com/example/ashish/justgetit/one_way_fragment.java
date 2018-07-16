package com.example.ashish.justgetit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class one_way_fragment extends android.support.v4.app.Fragment {
    private static final String TAG = "one_way_fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one_way_fragment, container, false);
        return view;
    }
}
