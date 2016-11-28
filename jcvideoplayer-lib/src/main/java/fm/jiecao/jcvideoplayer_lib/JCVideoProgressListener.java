package fm.jiecao.jcvideoplayer_lib;

/**
 * Created by LeeQ on 2016-11-26.
 */
public interface JCVideoProgressListener {
    void onProgress(int duration, int progress);

    void onVideoComplete();
}
