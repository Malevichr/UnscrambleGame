package com.ru.malevich.unscramblegame.views.unscrambledwordedittext

import java.io.Serializable

interface InputUiState : Serializable {
    fun update(scrambledTextView: UpdateUnscrambledEditText)

    object Initial : InputUiState {
        private fun readResolve(): Any = Initial
        override fun update(scrambledTextView: UpdateUnscrambledEditText) {
            scrambledTextView.update(
                availabilityToInput = true,
                clearText = true
            )
        }
    }

    object Base : InputUiState {
        private fun readResolve(): Any = Base
        override fun update(scrambledTextView: UpdateUnscrambledEditText) {
            scrambledTextView.update(
                availabilityToInput = true,
                clearText = false
            )
        }
    }

    object RightAnswered : InputUiState {
        private fun readResolve(): Any = RightAnswered
        override fun update(scrambledTextView: UpdateUnscrambledEditText) {
            scrambledTextView.update(
                availabilityToInput = false,
                clearText = false
            )
        }
    }
}