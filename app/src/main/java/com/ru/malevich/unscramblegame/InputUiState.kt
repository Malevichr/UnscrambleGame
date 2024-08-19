package com.ru.malevich.unscramblegame

import com.ru.malevich.unscramblegame.databinding.ActivityMainBinding

interface InputUiState : UiState {
    abstract class Abstract(
        private val availabilityToInput: Boolean,
        private val clearText: Boolean
    ) : InputUiState {
        override fun update(binding: ActivityMainBinding) = with(binding) {
            inputLayout.isEnabled = availabilityToInput
            if (clearText)
                binding.inputText.text?.clear()
        }
    }

    class Initial : Abstract(
        availabilityToInput = true,
        clearText = true
    )

    class Base : Abstract(
        availabilityToInput = true,
        clearText = false
    )

    class RightAnswered : Abstract(
        availabilityToInput = false,
        clearText = false
    )
}