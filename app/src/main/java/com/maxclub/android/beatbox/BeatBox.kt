package com.maxclub.android.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException

private const val LOG_TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

class BeatBox(private val assets: AssetManager) {
    val sounds: List<Sound>
    val defaultRate: Float = 1.0f
    val minRate: Float = 0.5f
    val maxRate: Float = 2.0f

    var rate: Float = defaultRate
        set(value) {
            field = value
            streamIds.forEach {
                soundPool.setRate(it, value)
            }
        }

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    private val streamIds = mutableListOf<Int>()

    init {
        sounds = loadSounds()
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            val streamId = soundPool.play(it, 1.0f, 1.0f, 1, 0, rate)
            addStreamId(streamId)
        }
    }

    fun release() {
        soundPool.release()
    }

    private fun loadSounds(): List<Sound> {
        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Could not list assets", e)
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()
        soundNames.forEach {
            val sound = Sound("$SOUNDS_FOLDER/$it")
            try {
                load(sound)
                sounds += sound
            } catch (ioe: IOException) {
                Log.e(LOG_TAG, "Could not load sound $it", ioe)
            }
        }

        return sounds
    }

    private fun load(sound: Sound) {
        val assetFileDescriptor: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(assetFileDescriptor, 1)
        sound.soundId = soundId
    }

    private fun addStreamId(streamId: Int) {
        if (streamIds.size == MAX_SOUNDS) {
            streamIds.removeLast()
        }
        streamIds.add(0, streamId)
    }
}