package com.ru.malevich.unscramblegame.data

data class UnscrambleTask(
    val unscrambledWord: String,
    val scrambledWord: String
) {
    fun checkUserAnswer(userAnswer: String): Boolean =
        userAnswer.equals(unscrambledWord, true)
}