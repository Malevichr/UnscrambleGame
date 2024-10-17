package com.ru.malevich.unscramblegame.gameover.data

import com.ru.malevich.unscramblegame.core.data.IntCache

interface GameOverRepository {
    fun stats(): Pair<Int, Int>
    fun clearStats()
    class Base(
        private val corrects: IntCache,
        private val incorrects: IntCache
    ) : GameOverRepository {
        override fun stats(): Pair<Int, Int> {
            return Pair(corrects.read(), incorrects.read())
        }

        override fun clearStats() {
            corrects.default()
            incorrects.default()
        }
    }
}