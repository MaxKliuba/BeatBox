package com.maxclub.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.google.android.material.slider.Slider
import java.util.*

class BeatBoxViewModel(val beatBox: BeatBox) : BaseObservable() {
    val minRate: Float
        get() = beatBox.minRate

    val maxRate: Float
        get() = beatBox.maxRate

    val rate: Float
        get() = beatBox.rate

    @get:Bindable
    val rateLabel: String
        get() = "x${"%.1f".format(Locale.US, beatBox.rate)}"

    fun onRateSliderValueChanged(value: Float) {
        beatBox.rate = value
        notifyChange()
    }

    fun onRateButtonClicked() {
        beatBox.rate = beatBox.defaultRate
        notifyChange()
    }
}

interface OnValueChangeListener {
    fun onValueChanged(value: Float)
}

@BindingAdapter(value = ["onValueChangeListener"])
fun setOnValueChangeListener(slider: Slider, listener: OnValueChangeListener) {
    slider.addOnChangeListener { _: Slider?, value: Float, _: Boolean ->
        listener.onValueChanged(value)
    }
}