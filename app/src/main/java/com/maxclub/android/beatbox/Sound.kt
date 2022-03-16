package com.maxclub.android.beatbox

private const val FILENAME_EXTENSION = ".wav"

class Sound(val assetPath: String, var soundId: Int? = null) {
    val name = assetPath.split("/").last().removeSuffix(FILENAME_EXTENSION)
}