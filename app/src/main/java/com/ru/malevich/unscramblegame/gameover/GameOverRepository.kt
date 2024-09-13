package com.ru.malevich.unscramblegame.gameover

interface GameOverRepository {
    fun stats(): Pair<Int, Int>
}