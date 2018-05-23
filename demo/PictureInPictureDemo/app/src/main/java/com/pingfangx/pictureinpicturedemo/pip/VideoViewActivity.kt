package com.pingfangx.pictureinpicturedemo.pip

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import com.pingfangx.pictureinpicturedemo.R
import kotlinx.android.synthetic.main.activity_video_view.*

class VideoViewActivity : BasePipActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_view)
        initViews()
    }

    override fun initViews() {
        video_view.setMediaController(object : MediaController(this) {
        })
        val uri = Uri.parse("android.resource://$packageName/${R.raw.vid_bigbuckbunny}");
        video_view.setVideoURI(uri)
        video_view.start()
    }

}
