package com.ru.malevich.unscramblegame

interface GameRepository {

    fun unscrambleTask(): UnscrambleTask
    fun next()
    class Base(
        private val list: List<String> = listOf(
            "auto",
            "animal",
            "car"
        )
    ) : GameRepository {

        private var listIndex = 0
        override fun unscrambleTask(): UnscrambleTask {
            val word: String = list[listIndex]
            return UnscrambleTask(unscrambledWord = word, scrambledWord = word.reversed())
        }

        override fun next() {
            listIndex = ++listIndex % list.size
        }
    }
}
