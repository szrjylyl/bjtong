package com.pri.lon.boyin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pri.lon.boyin.R;

/**
 * Created by Lyon on 18/1/4.
 */

public class RechargeFragment extends Fragment {


    public static RechargeFragment newInstance(){

        return new RechargeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recharge,null);

        return view;
    }
}
