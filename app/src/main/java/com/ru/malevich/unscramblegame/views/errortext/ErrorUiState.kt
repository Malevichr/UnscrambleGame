package com.ru.malevich.unscramblegame.views.errortext

import android.view.View
import java.io.Serializable

interface ErrorUiState : Serializable {
    fun update(updateError: UpdateError)

    object Hide : ErrorUiState {
        override fun update(updateError: UpdateError) {
            updateError.updateVisibility(View.GONE)
        }
    }

    class Show(private val textResId: Int) : ErrorUiState {
        override fun update(updateError: UpdateError) {
            updateError.updateVisibility(View.VISIBLE)
            updateError.updateTextResId(textResId)
        }
    }
}
