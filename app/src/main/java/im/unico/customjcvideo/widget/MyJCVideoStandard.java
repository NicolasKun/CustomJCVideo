/**
 * Copyright (c) 2016, lqiang0219@gmail.com All Rights Reserved.
 * The coding is soooooooooooooooooooooooooooooooooooooo
 * ⊂_ヽ
 *    ＼＼ ＿
 *    　　 ＼(　•_•) F
 *    　　　 <　⌒ヽ A
 *    　　　/ 　 へ＼ B
 *    　　 /　　/　＼＼ U
 *    　　 ﾚ　ノ　　 ヽ_つ L
 *    　　 /　/ O
 *    　 /　/ U
 *    　(　(ヽ S
 *    　|　|、＼.
 *    　| 丿 ＼ ⌒)
 *    　| |　　) /
 *     ノ )　 Lﾉ__
 *    (／___﻿
 */
package im.unico.customjcvideo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import im.unico.customjcvideo.R;

/**
 * Created by LeeQ on 2016-11-17.
 * 继承JCVideoPlayerStandard 实现添加新控件的功能
 *
 * 接下来的计划
 * * 根据工作需求 在播放15s后停止并出现提示画面
 * * 播放完成后出现特定画面
 */
public class MyJCVideoStandard extends JCVideoPlayerStandard {

    private static final String TAG = "MyJCVideoStandard";

    public ImageView customBtn;
    private OnClickCustomBtnListener listener;

    public MyJCVideoStandard(Context context) {
        super(context);
    }

    public MyJCVideoStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写该方法使之返回本project布局
     */
    @Override
    public int getLayoutId() {
        return R.layout.jc_layout_standard;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        customBtn = ((ImageView) findViewById(R.id.custom_btn));
        customBtn.setOnClickListener(this);
    }

    /**
     * 重写此方法使backButton在普通状态下也显示出来
     */
    @Override
    public boolean setUp(String url, int screen, Object... objects) {
        if (objects.length == 0) return false;
        if (super.setUp(url, screen, objects)) {
            titleTextView.setText(objects[0].toString());
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                fullscreenButton.setImageResource(fm.jiecao.jcvideoplayer_lib.R.drawable.jc_shrink);
                backButton.setVisibility(View.VISIBLE);
                tinyBackImageView.setVisibility(View.INVISIBLE);
            } else if (currentScreen == SCREEN_LAYOUT_NORMAL
                    || currentScreen == SCREEN_LAYOUT_LIST) {
                fullscreenButton.setImageResource(fm.jiecao.jcvideoplayer_lib.R.drawable.jc_enlarge);
                backButton.setVisibility(View.VISIBLE);
                tinyBackImageView.setVisibility(View.INVISIBLE);
            } else if (currentScreen == SCREEN_WINDOW_TINY) {
                tinyBackImageView.setVisibility(View.VISIBLE);
                setAllControlsVisible(View.INVISIBLE, View.INVISIBLE, View.INVISIBLE,
                        View.INVISIBLE, View.INVISIBLE, View.INVISIBLE, View.INVISIBLE);
            }
            return true;
        }
        return false;
    }

    /**
     * 重写该方法使
     * * 自定义按钮设置点击事件
     * * 重写返回按钮的点击事件
     */
    @Override
    public void onClick(View v) {
        Log.e(TAG, "onClick: " + currentState);
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.custom_btn) {
            if (currentScreen == SCREEN_LAYOUT_LIST) customBtn.setVisibility(VISIBLE);
            else customBtn.setVisibility(GONE);

            if (currentState == CURRENT_STATE_PLAYING) {
                JCMediaManager.instance().mediaPlayer.pause();
                setUiWitStateAndScreen(CURRENT_STATE_PAUSE);
            }
            listener.onCustom();

        } else if (id == R.id.back) {
            if (currentScreen == SCREEN_LAYOUT_NORMAL
                    || currentScreen == SCREEN_LAYOUT_LIST) {
                listener.onBack();
            } else {
                backPress();
            }
        }
    }

    //设置监听
    public void setCustomBtnListener(OnClickCustomBtnListener listener) {
        this.listener = listener;
    }

    /**
     * 定义回调接口
     * * onCustom 自定义按钮
     * * onBack 重写返回按钮
     */
    public interface OnClickCustomBtnListener{
        void onCustom();

        void onBack();
    }


}
