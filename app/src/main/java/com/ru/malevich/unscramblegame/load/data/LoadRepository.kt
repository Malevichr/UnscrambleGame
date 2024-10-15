package com.ru.malevich.unscramblegame.load.data

import com.ru.malevich.unscramblegame.load.data.cloud.CloudDataSource

interface LoadRepository {

    suspend fun load()

    class Base(
        private val cloudDataSource: CloudDataSource,
    ) : LoadRepository {
        override suspend fun load() {
            val words: List<String> = cloudDataSource.load()
        }
    }
}
