package com.ru.malevich.unscramblegame

data class UnscrambleTask(
    val unscrambledWord: String,
    val scrambledWord: String
) {
    fun checkUserAnswer(userAnswer: String): Boolean =
        userAnswer.equals(userAnswer, true)
}