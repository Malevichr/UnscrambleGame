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
            scrambledWord.length -> GameUiState.SufficientInput(scrambledWord)
            else -> GameUiState.InsufficientInput(scrambledWord)
        }
    }

    fun init(): GameUiState {
        val data = repository.unscrambleTask()
        val savedInput = repository.userInput()
        return if (savedInput == "")
            GameUiState.Initial(data.scrambledWord)
        else if (data.unscrambledWord.length == savedInput.length)
            GameUiState.SufficientInput(data.scrambledWord, savedInput)
        else
            GameUiState.InsufficientInput(data.scrambledWord, savedInput)

    }

}
