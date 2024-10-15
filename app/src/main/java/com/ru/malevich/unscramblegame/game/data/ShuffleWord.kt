package com.ru.malevich.unscramblegame.game.data

interface ShuffleWord {
    fun shuffle(word: String): String
    class Reverse : ShuffleWord {
        override fun shuffle(word: String): String = word.reversed()
    }

    class None : ShuffleWord {
        override fun shuffle(word: String): String = word
    }
}