package com.example.shifumi.utils

import android.content.Context
import android.media.MediaPlayer

class MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun startMusic(context: Context, resId: Int) {
        mediaPlayer = MediaPlayer.create(context, resId)
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}