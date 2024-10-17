package com.ru.malevich.unscramblegame.views.checkbutton

import java.io.Serializable

interface CheckUiState : Serializable {
    fun update(checkButton: CheckButton)

    object Disabled : CheckUiState {
        private fun readResolve(): Any = Disabled
        override fun update(checkButton: CheckButton) {
            checkButton.update(
                clickable = false,
                enabled = false,
                color = "#5451ED"
            )
        }
    }

    object Enabled : CheckUiState {
        private fun readResolve(): Any = Enabled
        override fun update(checkButton: CheckButton) {
            checkButton.update(
                clickable = true,
                enabled = true,
                color = "#5451ED"
            )
        }
    }

    object RightAnswered : CheckUiState {
        private fun readResolve(): Any = RightAnswered
        override fun update(checkButton: CheckButton) {
            checkButton.update(
                clickable = false,
                enabled = true,
                color = "#36FF17"
            )
        }
    }

    object WrongAnswered : CheckUiState {
        private fun readResolve(): Any = WrongAnswered
        override fun update(checkButton: CheckButton) {
            checkButton.update(
                clickable = false,
                enabled = true,
                color = "#FF3232"
            )
        }
    }
}