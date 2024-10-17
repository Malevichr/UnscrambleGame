package com.ru.malevich.unscramblegame.game.presentation

import com.ru.malevich.unscramblegame.core.di.ClearViewModel
import com.ru.malevich.unscramblegame.core.presentation.GameUiState
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.game.data.GameRepository
import com.ru.malevich.unscramblegame.load.presentation.RunAsync

class GameViewModel(
    private val repository: GameRepository,
    private val clearViewModel: ClearViewModel,
    runAsync: RunAsync,
    observable: GameUiObservable
) : MyViewModel.Async.Abstract<GameUiState>(runAsync, observable) {

    fun next() {
        repository.incCorrects()
        if (repository.isLastQuestion()) {
            clearViewModel.clear(GameViewModel::class.java)
            repository.clearProgress()
            observable.postUiState(GameUiState.Finish)
        } else {
            repository.next()
            init()
        }
    }

    fun skip() {
        repository.incIncorrects()
        if (repository.isLastQuestion()) {
            clearViewModel.clear(GameViewModel::class.java)
            repository.clearProgress()
            observable.postUiState(GameUiState.Finish)
        } else {
            repository.next()
            init()
        }
    }

    fun check(text: String) {
        handleAsync {
            val data = repository.unscrambleTask()

            if (data.checkUserAnswer(text)) {
                GameUiState.RightAnswered(data.scrambledWord, text)
            } else
                GameUiState.WrongAnswered(data.scrambledWord, text)
        }
    }

    fun handleUserInput(text: String) {
        handleAsync {
            repository.saveUserInput(text)
            val scrambledWord = repository.unscrambleTask().scrambledWord

            if (text.length == scrambledWord.length)
                GameUiState.SufficientInput(scrambledWord, text)
            else
                GameUiState.InsufficientInput(scrambledWord, text)
        }
    }

    fun init(firstRun: Boolean = true) {
        if (firstRun) {
            handleAsync {
                val savedInput = repository.userInput()
                val scrambledWord = repository.unscrambleTask().scrambledWord

                if (savedInput == "")
                    GameUiState.Initial(scrambledWord)
                else if (savedInput.length == scrambledWord.length) {
                    GameUiState.SufficientInput(scrambledWord, savedInput)
                } else
                    GameUiState.InsufficientInput(scrambledWord, savedInput)
            }
        }
    }
}
