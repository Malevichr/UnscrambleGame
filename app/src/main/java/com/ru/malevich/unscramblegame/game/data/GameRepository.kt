package com.ru.malevich.unscramblegame.game.data

import com.ru.malevich.unscramblegame.core.data.BooleanCache
import com.ru.malevich.unscramblegame.core.data.IntCache
import com.ru.malevich.unscramblegame.core.data.StringCache

interface GameRepository {

    fun unscrambleTask(): UnscrambleTask
    fun saveUserInput(input: String)
    fun userInput(): String
    fun saveChecked(boolean: Boolean)
    fun isChecked(): Boolean
    fun next()
    fun clearProgress()
    fun incCorrects()
    fun incIncorrects()
    fun isLastQuestion(): Boolean

    class Base(
        private val listIndex: IntCache,
        private val userInputText: StringCache,
        private val checked: BooleanCache,
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val shuffleWord: ShuffleWord,
        private val size: Int
    ) : GameRepository {
        override fun unscrambleTask(): UnscrambleTask {
//            val word: String = list[listIndex.read()]
            return UnscrambleTask(
                unscrambledWord = "word",
                scrambledWord = shuffleWord.shuffle("word")
            )
        }

        override fun saveUserInput(input: String) {
            userInputText.save(input)
        }

        override fun userInput(): String {
            return userInputText.read()
        }

        override fun saveChecked(boolean: Boolean) {
            checked.save(boolean)
        }

        override fun isChecked() = checked.read()

        override fun next() {
//            listIndex.save((listIndex.read() + 1) % list.size)
            userInputText.save("")
        }

        override fun clearProgress() {
            listIndex.default()
            checked.save(false)
            userInputText.save("")
        }

        override fun isLastQuestion(): Boolean =
            listIndex.read() + 1 == size

        override fun incCorrects() {
            corrects.increment()
        }

        override fun incIncorrects() {
            incorrects.increment()
        }

    }

    class Fake(
        private val listIndex: IntCache,
        private val userInputText: StringCache,
        private val checked: BooleanCache,
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val shuffleWord: ShuffleWord,
        private val list: List<String> = listOf(
            "auto",
            "animal",
        ),

        ) : GameRepository {
        override fun unscrambleTask(): UnscrambleTask {
            val word: String = list[listIndex.read()]
            return UnscrambleTask(unscrambledWord = word, scrambledWord = shuffleWord.shuffle(word))
        }

        override fun saveUserInput(input: String) {
            userInputText.save(input)
        }

        override fun userInput(): String {
            return userInputText.read()
        }
        override fun saveChecked(boolean: Boolean) {
            checked.save(boolean)
        }

        override fun isChecked() = checked.read()

        override fun next() {
            listIndex.save((listIndex.read() + 1) % list.size)
            userInputText.save("")
        }

        override fun clearProgress() {
            listIndex.default()
            checked.save(false)
            userInputText.save("")
        }

        override fun isLastQuestion(): Boolean =
            listIndex.read() + 1 == list.size

        override fun incCorrects() {
            corrects.increment()
        }

        override fun incIncorrects() {
            incorrects.increment()
        }
    }
}
