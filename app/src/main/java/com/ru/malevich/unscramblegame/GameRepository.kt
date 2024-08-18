package com.ru.malevich.unscramblegame

interface GameRepository {

    fun unscrambleTask(): UnscrambleTask
    fun next()
}
