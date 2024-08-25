package com.ru.malevich.unscramblegame.view

import android.graphics.Color
import com.ru.malevich.unscramblegame.databinding.ActivityMainBinding

interface CheckUiState : UiState {
    abstract class Abstract(
        private val checkIsClickable: Boolean,
        private val checkIsEnabled: Boolean,
        private val checkColor: String
    ) : CheckUiState {
        override fun update(binding: ActivityMainBinding) = with(binding) {
            checkButton.isClickable = checkIsClickable
            checkButton.setBackgroundColor(Color.parseColor(checkColor))
            checkButton.isEnabled = checkIsEnabled
        }
    }

    class Disabled : Abstract(
        checkIsClickable = false,
        checkIsEnabled = false,
        checkColor = "#5451ED"
    )

    class Enabled : Abstract(
        checkIsClickable = true,
        checkIsEnabled = true,
        checkColor = "#5451ED"
    )

    class RightAnswered : Abstract(
        checkIsClickable = false,
        checkIsEnabled = true,
        checkColor = "#36FF17"
    )

    class WrongAnswered : Abstract(
        checkIsClickable = false,
        checkIsEnabled = true,
        checkColor = "#FF3232"
    )
}