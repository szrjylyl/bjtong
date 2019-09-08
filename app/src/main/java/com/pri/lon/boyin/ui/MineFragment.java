package com.pri.lon.boyin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pri.lon.boyin.common.Constant;
import com.pri.lon.boyin.R;
import com.pri.lon.boyin.VideoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Lyon on 18/1/4.
 */

public class MineFragment extends Fragment {

    private Unbinder binder;

    public static MineFragment newInstance(){

        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine,null);
        binder = ButterKnife.bind(this,view);

        return view;
    }

    @OnClick({R.id.skip})
    public void onClick(View view){

        switch (view.getId()){
            case R.id.skip:
                VideoActivity.launch(getActivity(), Constant.URL_1);
             break;

        }

    }

    @Override
    public void onDestroyView() {
        if(binder != null){
            binder.unbind();
        }
        super.onDestroyView();
    }
}
