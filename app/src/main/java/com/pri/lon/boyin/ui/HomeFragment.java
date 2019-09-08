package com.pri.lon.boyin.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pri.lon.boyin.R;
import com.pri.lon.boyin.VideoActivity;
import com.pri.lon.boyin.common.Constant;
import com.pri.lon.boyin.model.Video;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Lyon on 18/1/4.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private Unbinder binder;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Video> urlList;


    public static HomeFragment newInstance(){
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home,null);
        binder = ButterKnife.bind(this,view);

        init();
        return view;
    }


    private void init(){
        mLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdapter = new MyAdapter(getData());
        mRecyclerView.setAdapter(mAdapter);
    }

    private List getData() {
        urlList = new ArrayList<>();
        Video video;
        for (int i = 0; i < 20; i++) {
            video = new Video();
            video.des = i + "=" + i;
            video.url = Constant.URL_1;
            urlList.add(video);
        }
        Log.e("Lyon",urlList.toString());
        return urlList;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        public List<Video> datas;

        public MyAdapter(List<Video> list){
            this.datas = list;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder holder, final int position) {
            final Video video = datas.get(position);
            holder.mTextView.setText(video.des);

            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoActivity.launch(getActivity(), video.url);
                }
            });

        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView mTextView;
            public ImageView img;
            public LinearLayout itemLayout;

            public ViewHolder(View view){
                super(view);
                mTextView = (TextView)view.findViewById(R.id.name);
                img = (ImageView)view.findViewById(R.id.img);
                itemLayout = (LinearLayout) view.findViewById(R.id.itemLayout);
            }
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
