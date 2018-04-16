package com.github.stephenvinouze.kontinuousspeechrecognizer

import android.content.Context
import android.media.AudioManager

object AudioUtils {

    @JvmStatic fun muteAudio(shouldMute: Boolean, context: Context)
    {
        val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val muteValue = if (shouldMute) AudioManager.ADJUST_MUTE else AudioManager.ADJUST_UNMUTE
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, muteValue, 0)
    }

}