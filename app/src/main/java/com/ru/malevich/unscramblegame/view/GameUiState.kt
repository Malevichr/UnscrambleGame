package com.ru.malevich.unscramblegame.view

import android.view.View
import com.ru.malevich.unscramblegame.databinding.ActivityMainBinding

interface GameUiState : UiState {
    abstract class Abstract(
        private val text: String,
        private val inputUiState: InputUiState,
        private val checkUiState: CheckUiState,
        private val skipVisibility: Int,
        private val nextVisibility: Int,
        private val userInput: String = ""
    ) : GameUiState {
        override fun update(binding: ActivityMainBinding) = with(binding) {
            scrambledWordTextView.text = text
            inputUiState.update(binding)
            checkUiState.update(binding)
            skipButton.visibility = skipVisibility
            nextButton.visibility = nextVisibility
            if (userInput != "")
                inputText.setText(userInput)
        }
    }

    data class Initial(val scrambledWord: String, private val userInput: String = "") : Abstract(
        text = scrambledWord,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.GONE,
        inputUiState = InputUiState.Initial(),
        checkUiState = CheckUiState.Disabled(),
        userInput = userInput
    )

    data class InsufficientInput(val scrambledWord: String, private val userInput: String = "") :
        Abstract(
        text = scrambledWord,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.GONE,
        inputUiState = InputUiState.Base(),
            checkUiState = CheckUiState.Disabled(),
            userInput = userInput
    )

    data class SufficientInput(val scrambledWord: String, private val userInput: String = "") :
        Abstract(
        text = scrambledWord,
        skipVisibility = View.VISIBLE,
        nextVisibility = View.GONE,
        inputUiState = InputUiState.Base(),
            checkUiState = CheckUiState.Enabled(),
            userInput = userInput
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

