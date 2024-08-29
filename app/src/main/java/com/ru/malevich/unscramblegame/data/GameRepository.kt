package com.ru.malevich.unscramblegame.data

interface GameRepository {

    fun unscrambleTask(): UnscrambleTask
    fun saveUserInput(input: String)
    fun userInput(): String
    fun next()
    class Base(
        private val listIndex: IntCache,
        private val userInputText: StringCache,
        private val list: List<String> = listOf(
            "auto",
            "animal",
            "car"
        )
    ) : GameRepository {
        override fun unscrambleTask(): UnscrambleTask {
            val word: String = list[listIndex.read()]
            return UnscrambleTask(unscrambledWord = word, scrambledWord = word.reversed())
        }

        override fun saveUserInput(input: String) {
            userInputText.save(input)
        }

        override fun userInput(): String {
            return userInputText.read()
        }

        override fun next() {
            listIndex.save((listIndex.read() + 1) % list.size)
            userInputText.save("")
        }
    }
}
