# CustomJCVideo
在JCVideoPlayerStandard基础上修改  仿制网易云音乐视频播放界面





##注：

在某些情况下activity进入onPause()
比如第三方分享、回复消息、接个电话亦或是主动熄屏了
那么这时候我们需要暂停视频并且在用户回来后可以继续点击播放   不会重新开始
在JCVideoPlayer中我们知道
```
JCVideoPlayer.releaseAllVideos();
```
这是用来释放所有视频  再次播放将重新开始
所以这是用在onDestroy()的

而想让其单纯的暂停视频呢?
我们可以这样做
```
    @Override
    protected void onPause() {
        super.onPause();
        int state = vdVideoPlayer.getState();  //视频目前的状态
        if (vdVideoPlayer != null && state == JCVideoPlayer.CURRENT_STATE_PLAYING) { //如果正在播放
            JCMediaManager.instance().mediaPlayer.pause();  //暂停 ↓并将UI设置为暂停时的状态
            vdVideoPlayer.setUiWitStateAndScreen(JCVideoPlayer.CURRENT_STATE_PAUSE);
        }
    }
```
