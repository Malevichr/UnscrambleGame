package com.ru.malevich.unscramblegame

import com.ru.malevich.unscramblegame.databinding.ActivityMainBinding

interface GameUiState {
    fun update(binding: ActivityMainBinding) {
        TODO("Not yet implemented")
    }

    data class Initial(val scrambledWord: String) : GameUiState {

    }

    data class InsufficientInput(val scrambledWord: String) :
        GameUiState {

    }

    data class SufficientInput(val scrambledWord: String) :
        GameUiState {

    }

    data class RightAnswered(val scrambledWord: String) :
        GameUiState {

    }

    data class WrongAnswered(
        val scrambledWord: String
    ) : GameUiState {

    }


}

interface ButtonUiState {
    class RightAnswered : ButtonUiState {

    }

    class WrongAnswered : ButtonUiState {

    }

}