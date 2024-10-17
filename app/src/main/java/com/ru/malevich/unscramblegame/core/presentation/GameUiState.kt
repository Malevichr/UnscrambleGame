package com.ru.malevich.unscramblegame.core.presentation

import com.ru.malevich.quizgame.views.visibilitybutton.UpdateVisibility
import com.ru.malevich.quizgame.views.visibilitybutton.VisibilityUiState
import com.ru.malevich.unscramblegame.gameover.presentation.NavigateToGameOver
import com.ru.malevich.unscramblegame.views.checkbutton.CheckUiState
import com.ru.malevich.unscramblegame.views.checkbutton.UpdateCheckButton
import com.ru.malevich.unscramblegame.views.scrambledwordtextview.UpdateText
import com.ru.malevich.unscramblegame.views.unscrambledwordedittext.InputUiState
import com.ru.malevich.unscramblegame.views.unscrambledwordedittext.UpdateUnscrambledEditText

interface GameUiState : UiState {
    fun update(
        scrambledTextView: UpdateText,
        unscrambledEditText: UpdateUnscrambledEditText,
        checkButton: UpdateCheckButton,
        nextButton: UpdateVisibility,
        skipButton: UpdateVisibility
    )
    fun navigate(navigate: NavigateToGameOver) = Unit

    object Finish : GameUiState {
        override fun update(
            scrambledTextView: UpdateText,
            unscrambledEditText: UpdateUnscrambledEditText,
            checkButton: UpdateCheckButton,
            nextButton: UpdateVisibility,
            skipButton: UpdateVisibility
        ) = Unit

        override fun navigate(navigate: NavigateToGameOver) {
            navigate.navigateToGameOver()
        }
    }
    data class Initial(private val scrambledWord: String) : GameUiState {
        override fun update(
            scrambledTextView: UpdateText,
            unscrambledEditText: UpdateUnscrambledEditText,
            checkButton: UpdateCheckButton,
            nextButton: UpdateVisibility,
            skipButton: UpdateVisibility
        ) {
            scrambledTextView.updateText(scrambledWord)
            unscrambledEditText.update(InputUiState.Initial)
            checkButton.update(CheckUiState.Disabled)
            nextButton.update(VisibilityUiState.Gone)
            skipButton.update(VisibilityUiState.Visible)
        }

    }

    data class InsufficientInput(
        private val scrambledWord: String,
        private val userInput: String = ""
    ) : GameUiState {
        override fun update(
            scrambledTextView: UpdateText,
            unscrambledEditText: UpdateUnscrambledEditText,
            checkButton: UpdateCheckButton,
            nextButton: UpdateVisibility,
            skipButton: UpdateVisibility
        ) {
            scrambledTextView.updateText(scrambledWord)
            unscrambledEditText.update(InputUiState.Base)
            unscrambledEditText.updateText(userInput)
            checkButton.update(CheckUiState.Disabled)
            nextButton.update(VisibilityUiState.Gone)
        }

    }

    data class SufficientInput(
        private val scrambledWord: String,
        private val userInput: String = ""
    ) :
        GameUiState {
        override fun update(
            scrambledTextView: UpdateText,
            unscrambledEditText: UpdateUnscrambledEditText,
            checkButton: UpdateCheckButton,
            nextButton: UpdateVisibility,
            skipButton: UpdateVisibility
        ) {
            scrambledTextView.updateText(scrambledWord)
            unscrambledEditText.update(InputUiState.Base)
            unscrambledEditText.updateText(userInput)
            checkButton.update(CheckUiState.Enabled)
            nextButton.update(VisibilityUiState.Gone)
        }

    }

    data class RightAnswered(private val scrambledWord: String, private val userInput: String = "")
        : GameUiState {
        override fun update(
            scrambledTextView: UpdateText,
            unscrambledEditText: UpdateUnscrambledEditText,
            checkButton: UpdateCheckButton,
            nextButton: UpdateVisibility,
            skipButton: UpdateVisibility
        ) {
            scrambledTextView.updateText(scrambledWord)
            unscrambledEditText.update(InputUiState.RightAnswered)
            unscrambledEditText.updateText(userInput)
            checkButton.update(CheckUiState.RightAnswered)
            nextButton.update(VisibilityUiState.Visible)
            skipButton.update(VisibilityUiState.Gone)
        }

    }

    data class WrongAnswered(private val scrambledWord: String, private val userInput: String = "")
        : GameUiState {
        override fun update(
            scrambledTextView: UpdateText,
            unscrambledEditText: UpdateUnscrambledEditText,
            checkButton: UpdateCheckButton,
            nextButton: UpdateVisibility,
            skipButton: UpdateVisibility
        ) {
            scrambledTextView.updateText(scrambledWord)
            unscrambledEditText.update(InputUiState.Base)
            unscrambledEditText.updateText(userInput)
            checkButton.update(CheckUiState.WrongAnswered)
            nextButton.update(VisibilityUiState.Gone)
            skipButton.update(VisibilityUiState.Visible)
        }

    }
}

