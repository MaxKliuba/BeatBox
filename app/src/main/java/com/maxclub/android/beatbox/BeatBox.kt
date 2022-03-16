package com.maxclub.android.beatbox

import android.content.res.AssetManager
import android.util.Log

private const val LOG_TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"

class BeatBox(private val assets: AssetManager) {
    val sounds: List<Sound> = loadSounds()

    private fun loadSounds(): List<Sound> {
        return try {
            val soundNames = assets.list(SOUNDS_FOLDER)!!
            soundNames.map {
                Sound("$SOUNDS_FOLDER/$it")
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Could not list assets", e)
            emptyList()
        }
    }
}