package com.pri.lon.boyin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.pri.lon.boyin.dialog.CustomDialog;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Lyon on 18/1/4.
 */

public class VideoActivity extends Activity {

    @BindView(R.id.video)
    VideoView mVideoView;
    @BindView(R.id.layer_bg)
    RelativeLayout layer_bg;
    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.Start)
    Button start;

    private String url;
//    private MediaController mMediaController;

    private MediaMetadataRetriever mmr;

    public static void launch(Context context, String url) {

        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("URL", url);
        context.startActivity(intent);

    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (mVideoView != null && mVideoView.isPlaying()) {
                mVideoView.pause();
                mVideoView.stopPlayback();
                showDialog();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        url = getIntent().getStringExtra("URL");

        if (!TextUtils.isEmpty(url)) {
            mVideoView.setVideoPath(Uri.parse(url).toString());
            createVideoThumbnail();
        } else {
            return;
        }

        mVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Toast.makeText(VideoActivity.this, "视频地址错误", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.e("Lyon", "准备完成" + "时长==" + mVideoView.getDuration());
            }
        });


    }

    private void createVideoThumbnail() {

        Observable<Bitmap> observable = Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap bitmap = null;
                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                int kind = MediaStore.Video.Thumbnails.MINI_KIND;
                if (Build.VERSION.SDK_INT >= 14) {
                    retriever.setDataSource(Uri.parse(url).toString(), new HashMap<String, String>());
                } else {
                    retriever.setDataSource(Uri.parse(url).toString());
                }
                bitmap = retriever.getFrameAtTime();
                e.onNext(bitmap);
                retriever.release();
            }
        });
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<Bitmap>() {
            @Override
            public void accept(Bitmap bitmap) throws Exception {
                progressbar.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                image.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
        });
    }

    private void showDialog() {
        CustomDialog customDialog = new CustomDialog();
        customDialog.show(getFragmentManager(), "video_view");
    }


    @OnClick({R.id.Start})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.Start:
                play();
                break;
        }

    }

    private void play() {
        layer_bg.setVisibility(View.GONE);

        mVideoView.start();
        mVideoView.requestFocus();

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mHandler.sendEmptyMessage(0);
//            }
//        }, 6000);

    }


    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (mVideoView != null) {
            mVideoView.suspend();
        }
        super.onDestroy();
    }
}
