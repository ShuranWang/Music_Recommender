package au.edu.unsw.infs3634.musicrecommender.activitys;

import android.app.Dialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import au.edu.unsw.infs3634.musicrecommender.LoadingDialog;
import au.edu.unsw.infs3634.musicrecommender.R;
import au.edu.unsw.infs3634.musicrecommender.base.BaseActivity;
import au.edu.unsw.infs3634.musicrecommender.beans.ListBean;
import au.edu.unsw.infs3634.musicrecommender.utils.StringUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.tv_total_time)
    TextView tvTotalTime;
    @BindView(R.id.imgv_play)
    ImageView imgvPlay;


    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_actor)
    TextView tvActor;
    @BindView(R.id.tv_cate)
    TextView tv_cate;
    @BindView(R.id.tv_score)
    TextView tv_score;
    @BindView(R.id.imgv_pic)
    ImageView imgvPic;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    private Dialog mLoadingDialog;


    ListBean dataBean;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private int progress;
    private boolean isBeginThread = false;
    private boolean isPause = true;
    private boolean isClickPause = false;
    MyHandler handler;
    private boolean isFirst;
    private boolean isOnpauseStatus = false;
    private Runnable runnable = new Runnable() {
        public void run() {
            if (mediaPlayer.isPlaying()) {
                int current = mediaPlayer.getCurrentPosition();
                Logger.e("------current:  " + current);
                seekBar.setProgress(current);
                tvCurrentTime.setText(getTime(mediaPlayer.getCurrentPosition()));
            }
            handler.postDelayed(runnable, 1000);
        }
    };


    private void doMessgae(Message msg) {
        switch (msg.what) {
            case 0x11:


                break;

            case 1:
                LoadingDialog.closeDialog(mLoadingDialog);
                break;
        }
    }

    private static class MyHandler extends Handler {
        WeakReference<MusicActivity> wrf = null;

        public MyHandler(MusicActivity activity) {
            wrf = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MusicActivity activity = wrf.get();
            if (activity == null) {
                return;
            }
            activity.doMessgae(msg);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        ButterKnife.bind(this);
        isFirst = true;
        tvTitle.setText("Music");
        tvTitleLeft.setText("Back");
        if (handler == null) {
            handler = new MyHandler(this);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            dataBean = (ListBean) bundle.getSerializable("bean");
            if (dataBean != null) {
                tvName.setText(StringUtil.getContentEmpty(dataBean.name));
                tvActor.setText("Singer：" + StringUtil.getContentEmpty(dataBean.people));
                tv_score.setText("Score：" + StringUtil.getContentEmpty(dataBean.score));
                tv_cate.setText("Category：" + StringUtil.getContentEmpty(dataBean.cate));
                Glide.with(this).load(dataBean.pic).into(imgvPic);

            }
        }

        initMediaPlayer(dataBean); // 初始化MediaPlayer

        initListener();
        initLoadingDialog(); //等待的loading框
    }


    //等待的loading框
    private void initLoadingDialog() {
        mLoadingDialog =  LoadingDialog.createLoadingDialog(this, true, "Loading...");
    }


    @OnClick({R.id.tv_title_left, R.id.imgv_play})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_title_left:
                onBackPressed();
                break;

            case R.id.imgv_play:
                if (isPause) {
                    isClickPause = false;
                    play();
                } else {
                    isClickPause = true;
                    pause();
                }
                break;

        }
    }


    //开启或关闭线程
    public void OpenOrCloseThread() {
        if (isBeginThread) {
            isPause = true;
            handler.removeCallbacks(runnable);
            isBeginThread = false;
            imgvPlay.setImageResource(R.mipmap.icon_video_play);
        } else {
            isPause = false;
            handler.postDelayed(runnable, 0);
            isBeginThread = true;
            imgvPlay.setImageResource(R.mipmap.icon_video_pause);
        }
    }


    //seekbar监听
    private void initListener() {
        // 为进度条添加进度更改事件
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            // 当进度条停止修改的时候触发
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // 取得当前进度条的刻度
                progress = seekBar.getProgress();

                if (mediaPlayer.isPlaying()) {
                    // 设置当前播放的位置
                    mediaPlayer.seekTo(progress);
                }
            }
        });
    }


    private void initMediaPlayer(ListBean bean) {

        isFirst = true;
//        stop();
//        mediaPlayer.release();
//        mediaPlayer = null;
//        mediaPlayer = MediaPlayer.create(this, R.raw.m_01); //R.raw.music
        try {
//            mediaPlayer.setDataSource("https://mp32.9ku.com/upload/128/2021/02/22/1055080.mp3");
            mediaPlayer.setDataSource(bean.url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (isFirst) {
                    isFirst = false;
                    tvCurrentTime.setText("00:00");
                    tvTotalTime.setText(getTime(mediaPlayer.getDuration()));
                    seekBar.setMax(mediaPlayer.getDuration());
                    if (!mediaPlayer.isPlaying()) {
                        mediaPlayer.start();
                    }
                    // 开始线程，更新进度条的刻度
                    OpenOrCloseThread();
                }

                handler.sendEmptyMessage(1); // 关闭loading
                if (isOnpauseStatus && !isClickPause) {
                    mediaPlayer.seekTo(progress);
                    mediaPlayer.start();
                    OpenOrCloseThread();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {

            }
        });
    }


    //时间格式转换
    protected String getTime(long millionSeconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millionSeconds);
        return simpleDateFormat.format(c.getTime());
    }


    protected void play() {
        // 开始线程，更新进度条的刻度
        OpenOrCloseThread();
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(progress);
            Logger.e("------------play--" + progress);
            mediaPlayer.start();
        }
    }


    protected void stop() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            OpenOrCloseThread();
        }
    }

    public void pause() {
        if (mediaPlayer.isPlaying()) {
            progress = mediaPlayer.getCurrentPosition();
            mediaPlayer.pause();
            OpenOrCloseThread();
        }

        Logger.e("------------pause--" + progress);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnpauseStatus && !isFirst) {
            play();
        }

         LoadingDialog.showDialog(mLoadingDialog);

    }

    @Override
    protected void onStop() {
        super.onStop();
        stop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnpauseStatus = true;
        isFirst = false;
        Logger.e("---------------onpause");
        pause();
    }
}
