package com.ru.malevich.unscramblegame

import com.ru.malevich.unscramblegame.data.GameRepository
import com.ru.malevich.unscramblegame.views.GameUiState

class GameViewModel(val repository: GameRepository) {
    fun next(): GameUiState {
        repository.next()
        return init()
    }

    fun check(text: String): GameUiState {
        val data = repository.unscrambleTask()
        return if (data.checkUserAnswer(text))
            GameUiState.RightAnswered
        else
            GameUiState.WrongAnswered
    }

    fun handleUserInput(
        text: String,
        scrambledWordRetrieved: String = ""
    ): GameUiState {
        val scrambledWord = if (scrambledWordRetrieved == "") {
            repository.saveUserInput(text)
            repository.unscrambleTask().scrambledWord
        } else
            scrambledWordRetrieved

        return if (text.length == scrambledWord.length)
            GameUiState.SufficientInput(text)
        else
            GameUiState.InsufficientInput(text)
    }

    fun init(firstRun: Boolean = true): GameUiState {
        return if (firstRun) {
            val savedInput = repository.userInput()
            val scrambledWord = repository.unscrambleTask().scrambledWord
            if (savedInput == "")
                GameUiState.Initial(scrambledWord)
            else
                handleUserInput(savedInput, scrambledWord)
        } else
            GameUiState.Empty
    }
}
