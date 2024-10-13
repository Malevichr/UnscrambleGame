package com.ru.malevich.unscramblegame.load.data

interface LoadRepository {

    suspend fun load(): Any
}
