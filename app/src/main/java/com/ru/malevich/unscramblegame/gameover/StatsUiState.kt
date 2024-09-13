package com.ru.malevich.unscramblegame.gameover

interface StatsUiState {
    data class Base(private val corrects: Int, private val incorrects: Int) : StatsUiState {

    }
}