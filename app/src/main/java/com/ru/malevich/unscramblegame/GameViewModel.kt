package com.ru.malevich.unscramblegame

import com.ru.malevich.unscramblegame.data.GameRepository
import com.ru.malevich.unscramblegame.view.GameUiState

class GameViewModel(val repository: GameRepository) {
    fun next(): GameUiState {
        repository.next()
        return init()
    }

    fun check(text: String): GameUiState {
        val data = repository.unscrambleTask()
        return if (data.checkUserAnswer(text))
            GameUiState.RightAnswered(data.scrambledWord)
        else
            GameUiState.WrongAnswered(data.scrambledWord)
    }

    fun handleUserInput(text: String): GameUiState {
        val scrambledWord = repository.unscrambleTask().scrambledWord
        repository.saveUserInput(text)
        return when (text.length) {
            0 -> GameUiState.Initial(scrambledWord)
            scrambledWord.length -> GameUiState.SufficientInput(scrambledWord, text)
            else -> GameUiState.InsufficientInput(scrambledWord, text)
        }
    }

    fun init(): GameUiState {
        val savedInput = repository.userInput()
        return handleUserInput(savedInput)
    }

}
