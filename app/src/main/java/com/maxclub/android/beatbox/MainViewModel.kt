package com.maxclub.android.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var isInitialized: Boolean = false

    lateinit var beatBox: BeatBox

    fun initialize(asset: AssetManager) {
        beatBox = BeatBox(asset)
        isInitialized = true
    }

    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }
}