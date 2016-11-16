package im.unico.customjcvideo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import im.unico.customjcvideo.R;

public class VideoDetail extends AppCompatActivity {

    @BindView(R.id.layout_title_iv_back)
    ImageView layoutTitleIvBack;
    @BindView(R.id.vd_title_layout)
    RelativeLayout vdTitleLayout;
    @BindView(R.id.vd_video_player)
    JCVideoPlayerStandard vdVideoPlayer;
    @BindView(R.id.layout_title_tv_title)
    TextView layoutTitleTvTitle;

    private String vTitle;
    private String vPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        vTitle = intent.getStringExtra("vTitle");
        vPath = intent.getStringExtra("vPath");
        init();
    }

    private void init() {
        layoutTitleTvTitle.setText(vTitle);
        setVideoPlayerDisplay();

        vdVideoPlayer.setUp(
                vPath,
                JCVideoPlayer.SCREEN_LAYOUT_LIST,
                ""
        );

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
        int state = vdVideoPlayer.getState();
        if (vdVideoPlayer != null && state == JCVideoPlayer.CURRENT_STATE_PLAYING) {
            //vdVideoPlayer.setUp(paths[abs], JCVideoPlayer.SCREEN_LAYOUT_LIST, "");
            //vdVideoPlayer.startButton.performClick();
            JCMediaManager.instance().mediaPlayer.pause();
            vdVideoPlayer.setUiWitStateAndScreen(JCVideoPlayer.CURRENT_STATE_PAUSE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
    }
}
