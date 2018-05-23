package com.pingfangx.pictureinpicturedemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.pingfangx.pictureinpicturedemo.autosize.AutoSizeTextViewActivity
import com.pingfangx.pictureinpicturedemo.pip.MediaPlayerActivity
import com.pingfangx.pictureinpicturedemo.pip.VideoViewActivity
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun onClickBtnPlayByVideoView(view: View) {
        startActivity<VideoViewActivity>()
    }

    public fun onClickBtnPlayByMediaPlayer(view: View) {
        startActivity<MediaPlayerActivity>()
    }

    public fun onClickBtnAutoSize(view: View) {
        startActivity<AutoSizeTextViewActivity>()
    }

    public fun onClickBtnViewTest(view: View) {
        startActivity<ViewTestActivity>()
    }
}
