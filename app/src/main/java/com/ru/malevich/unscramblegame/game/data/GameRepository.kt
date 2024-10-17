package com.ru.malevich.unscramblegame.game.data

import com.ru.malevich.unscramblegame.core.data.IntCache
import com.ru.malevich.unscramblegame.core.data.StringCache
import com.ru.malevich.unscramblegame.load.data.cache.WordsDao

interface GameRepository {

    suspend fun unscrambleTask(): UnscrambleTask
    fun saveUserInput(input: String)
    fun userInput(): String
    fun next()
    fun clearProgress()
    fun incCorrects()
    fun incIncorrects()
    fun isLastQuestion(): Boolean

    class Base(
        private val index: IntCache,
        private val userInputText: StringCache,
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val shuffleWord: ShuffleWord,
        private val dao: WordsDao,
        private val size: Int
    ) : GameRepository {
        override suspend fun unscrambleTask(): UnscrambleTask {
            val listIndex = index.read() % size
            this.index.save(listIndex)
            val word: String = dao.word(listIndex).word
            return UnscrambleTask(
                unscrambledWord = word,
                scrambledWord = shuffleWord.shuffle(word)
            )
        }

        override fun saveUserInput(input: String) {
            userInputText.save(input)
        }

        override fun userInput(): String {
            return userInputText.read()
        }

        override fun next() {
            index.save(index.read() % size + 1)
            userInputText.save("")
        }

        override fun clearProgress() {
            index.default()
            userInputText.save("")
        }

        override fun isLastQuestion(): Boolean =
            index.read() + 1 == size

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
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val shuffleWord: ShuffleWord,
        private val list: List<String> = listOf(
            "auto",
            "animal",
        ),

        ) : GameRepository {
        override suspend fun unscrambleTask(): UnscrambleTask {
            val listIndex = listIndex.read() % list.size
            this.listIndex.save(listIndex)
            val word: String = list[listIndex]
            return UnscrambleTask(unscrambledWord = word, scrambledWord = shuffleWord.shuffle(word))
        }

        override fun saveUserInput(input: String) {
            userInputText.save(input)
        }

        override fun userInput(): String {
            return userInputText.read()
        }

        override fun next() {
            listIndex.save(listIndex.read() % list.size + 1)
            userInputText.save("")
        }

        override fun clearProgress() {
            listIndex.default()
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
