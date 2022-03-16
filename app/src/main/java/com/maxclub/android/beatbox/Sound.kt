package com.maxclub.android.beatbox

private const val FILENAME_EXTENSION = ".wav"

class Sound(val assetPath: String) {
    val name = assetPath.split("/").last().removeSuffix(FILENAME_EXTENSION)
}