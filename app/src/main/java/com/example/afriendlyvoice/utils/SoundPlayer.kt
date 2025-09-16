package com.example.afriendlyvoice.utils

import android.content.Context
import android.media.MediaPlayer

object SoundPlayer {
    fun playSound(context: Context, soundResId: Int) {
        try {
            MediaPlayer.create(context, soundResId)?.start()
        } catch (e: Exception) {
            // Opcional: Manejar el error si el sonido no se puede reproducir
            e.printStackTrace()
        }
    }
}