package com.ru.malevich.unscramblegame.game

import com.ru.malevich.unscramblegame.views.GameUiState

class GameViewModel(val repository: GameRepository) {
    fun next(): GameUiState {
        repository.next()
        repository.saveChecked(false)
        repository.incCorrects()
        if (repository.isLastQuestion()) {
            repository.clearProgress()
            return GameUiState.Finish
        }
        return init()
    }

    fun check(text: String, checked: Boolean = false): GameUiState {
        val data = repository.unscrambleTask()
        if (!checked)
            repository.saveChecked(true)
        return if (data.checkUserAnswer(text))
            GameUiState.RightAnswered(data.scrambledWord, text)
        else
            GameUiState.WrongAnswered(data.scrambledWord, text)
    }

    fun handleUserInput(
        text: String,
        scrambledWordRetrieved: String = ""
    ): GameUiState {
        repository.saveChecked(false)
        val scrambledWord = if (scrambledWordRetrieved == "") {
            repository.saveUserInput(text)
            repository.unscrambleTask().scrambledWord
        } else
            scrambledWordRetrieved

        return if (text.length == scrambledWord.length)
            GameUiState.SufficientInput(scrambledWord, text)
        else
            GameUiState.InsufficientInput(scrambledWord, text)
    }

    fun init(firstRun: Boolean = true): GameUiState {
        return if (firstRun) {
            val savedInput = repository.userInput()
            val scrambledWord = repository.unscrambleTask().scrambledWord

            if (savedInput == "")
                GameUiState.Initial(scrambledWord)
            else
                if (repository.isChecked())
                    check(savedInput, true)
                else
                    handleUserInput(savedInput, scrambledWord)
        } else
            GameUiState.Empty
    }

    fun skip(): GameUiState {
        repository.next()
        repository.saveChecked(false)
        repository.incIncorrects()
        if (repository.isLastQuestion()) {
            repository.clearProgress()
            return GameUiState.Finish
        }

        return init()
    }
}
