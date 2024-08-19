package com.ru.malevich.unscramblegame

import android.view.View
import com.ru.malevich.unscramblegame.databinding.ActivityMainBinding

interface GameUiState : UiState {
    abstract class Abstract(
        private val text: String,
        private val inputUiState: InputUiState,
        private val checkUiState: CheckUiState,
        private val skipVisibility: Int,
        private val nextVisibility: Int

    ) : GameUiState {
        override fun update(binding: ActivityMainBinding) = with(binding) {
            scrambledWordTextView.text = text
            inputUiState.update(binding)
            checkUiState.update(binding)
            skipButton.visibility = skipVisibility
            nextButton.visibility = nextVisibility
        }
    }

    data class Initial(val scrambledWord: String) : Abstract(
        text = scrambledWord,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.GONE,
        inputUiState = InputUiState.Initial(),
        checkUiState = CheckUiState.Disabled()
    ) {
        override fun update(binding: ActivityMainBinding) {
            super.update(binding)

        }
    }

    data class InsufficientInput(val scrambledWord: String) : Abstract(
        text = scrambledWord,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.GONE,
        inputUiState = InputUiState.Base(),
        checkUiState = CheckUiState.Disabled()
    )

    data class SufficientInput(val scrambledWord: String) : Abstract(
        text = scrambledWord,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.GONE,
        inputUiState = InputUiState.Base(),
        checkUiState = CheckUiState.Enabled()
    )

    data class RightAnswered(val scrambledWord: String) : Abstract(
        text = scrambledWord,
        skipVisibility = View.GONE,
        nextVisibility = View.VISIBLE,
        inputUiState = InputUiState.RightAnswered(),
        checkUiState = CheckUiState.RightAnswered()
    )

    data class WrongAnswered(
        val scrambledWord: String
    ) : Abstract(
        text = scrambledWord,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.GONE,
        inputUiState = InputUiState.Base(),
        checkUiState = CheckUiState.WrongAnswered()
    )
}

