package com.pingfangx.pictureinpicturedemo.pip

import android.media.MediaPlayer
import android.os.Bundle
import android.view.SurfaceHolder
import com.pingfangx.pictureinpicturedemo.R
import kotlinx.android.synthetic.main.activity_media_player.*

class MediaPlayerActivity : BasePipActivity() {
    val mMediaPlayer: MediaPlayer by lazy {
        MediaPlayer.create(this, R.raw.vid_bigbuckbunny)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_player)
        initViews()
    }

    override fun initViews() {
        surface_view.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
            }

            override fun surfaceCreated(holder: SurfaceHolder?) {
                mMediaPlayer.setDisplay(holder)
                mMediaPlayer.start()
            }
        })
    }


}
