package im.unico.customjcvideo.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoProgressListener;
import im.unico.customjcvideo.R;
import im.unico.customjcvideo.widget.MyJCVideoStandard;

/**
 * 视频源来自 七牛云
 */
public class VideoDetail extends AppCompatActivity implements MyJCVideoStandard.OnClickCustomBtnListener, JCVideoProgressListener {

    private static final String TAG = "VideoDetail";

    @BindView(R.id.layout_title_iv_back)
    ImageView layoutTitleIvBack;
    @BindView(R.id.vd_title_layout)
    RelativeLayout vdTitleLayout;
    @BindView(R.id.layout_title_tv_title)
    TextView layoutTitleTvTitle;
    @BindView(R.id.vd_video_player)
    MyJCVideoStandard vdVideoPlayer;

    private String vTitle;
    private String vPath;
    private String vCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        vTitle = intent.getStringExtra("vTitle");
        vPath = intent.getStringExtra("vPath");
        vCover = intent.getStringExtra("vCover");
        init();
    }

    private void init() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(getResources().getColor(R.color.color_grey_900));

        setVideoPlayerDisplay();

        vdVideoPlayer.setUp(
                vPath,
                JCVideoPlayer.SCREEN_LAYOUT_LIST,
                ""
        );

        vdVideoPlayer.customBtn.setImageResource(R.mipmap.fenxiang);
        vdVideoPlayer.titleTextView.setText(vTitle);
        vdVideoPlayer.setCustomBtnListener(this);
        vdVideoPlayer.setFullScreenVisiable(View.GONE);
        vdVideoPlayer.setOnJCPlayingProgressListener(this);
    }

    //设置视频播放大小为 16/9
    private void setVideoPlayerDisplay() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        //Point point = new Point();
        DisplayMetrics metrics = new DisplayMetrics();
        defaultDisplay.getMetrics(metrics);

        int widthPixels = metrics.widthPixels;
        int i1 = widthPixels * 9 / 16;

        ViewGroup.LayoutParams params = vdVideoPlayer.getLayoutParams();
        params.height = i1;
        vdVideoPlayer.setLayoutParams(params);
    }

    @OnClick(R.id.layout_title_iv_back)
    public void onClick() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        int state = vdVideoPlayer.getState();  //视频目前的状态
        if (vdVideoPlayer != null && state == JCVideoPlayer.CURRENT_STATE_PLAYING) { //如果正在播放
            //JCMediaManager.instance().mediaPlayer.pause();  //暂停 ↓并将UI设置为暂停时的状态
            vdVideoPlayer.setUiWitStateAndScreen(JCVideoPlayer.CURRENT_STATE_PAUSE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
    }


    @Override
    public void onCustom() {
        Toast.makeText(this, "第三方分享", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBack() {
        finish();
    }

    @Override
    public void onProgress(int duration, int progress) {
        //子线程中
        Log.e(TAG, "onProgress: " + duration + " ---- " + progress);
        if (duration >= 15000 && progress >= 15000) {
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onVideoComplete() {
        Log.e(TAG, "onVideoComplete: 播放完成");
        Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(VideoDetail.this, "预览时间到", Toast.LENGTH_LONG).show();
                    JCVideoPlayer.releaseAllVideos();
                    vdVideoPlayer.thumbImageView.setVisibility(View.VISIBLE);
                    Glide.with(VideoDetail.this).load(vCover).into(vdVideoPlayer.thumbImageView);
                    break;
            }
        }
    };
}
