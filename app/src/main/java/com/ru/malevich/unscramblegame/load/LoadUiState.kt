package com.ru.malevich.unscramblegame.load

import com.ru.malevich.quizgame.views.visibilitybutton.UpdateVisibility
import com.ru.malevich.quizgame.views.visibilitybutton.VisibilityUiState
import com.ru.malevich.unscramblegame.game.NavigateToGame
import com.ru.malevich.unscramblegame.views.errortext.ErrorUiState
import com.ru.malevich.unscramblegame.views.errortext.UpdateError

interface LoadUiState {
    fun update(
        progressBar: UpdateVisibility,
        errorTextView: UpdateError,
        retryButton: UpdateVisibility
    ) = Unit

    fun navigate(navigateToGame: NavigateToGame) = Unit

    abstract class Abstract(
        private val progressState: VisibilityUiState,
        private val errorState: ErrorUiState,
        private val retryState: VisibilityUiState
    ) : LoadUiState {
        override fun update(
            progressBar: UpdateVisibility,
            errorTextView: UpdateError,
            retryButton: UpdateVisibility
        ) {
            progressBar.update(progressState)
            errorTextView.update(errorState)
            retryButton.update(retryState)
        }
    }

    object Progress : Abstract(
        progressState = VisibilityUiState.Visible,
        errorState = ErrorUiState.Hide,
        retryState = VisibilityUiState.Gone
    )

    class Error(errorMessage: String) : Abstract(
        progressState = VisibilityUiState.Invisible,
        errorState = ErrorUiState.ShowMessage(errorMessage),
        retryState = VisibilityUiState.Visible
    )

    object Success : LoadUiState {
        override fun navigate(navigateToGame: NavigateToGame) {
            navigateToGame.navigateToGame()
        }
    }
}
