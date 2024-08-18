package com.ru.malevich.unscramblegame

class GameViewModel(private val repository: GameRepository) {
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
        return when (text.length) {
            0 -> GameUiState.Initial(scrambledWord)
            scrambledWord.length -> GameUiState.SufficientInput(scrambledWord)
            else -> GameUiState.InsufficientInput(scrambledWord)
        }
    }

    fun init(): GameUiState {
        val data = repository.unscrambleTask()
        return GameUiState.Initial(
            data.scrambledWord
        )
    }

}
