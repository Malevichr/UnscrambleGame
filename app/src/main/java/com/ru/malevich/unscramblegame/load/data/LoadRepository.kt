package com.ru.malevich.unscramblegame.load.data

import com.ru.malevich.unscramblegame.load.data.cloud.CloudDataSource
import kotlinx.coroutines.delay

interface LoadRepository {

    suspend fun load()

    class Base(
        private val cloudDataSource: CloudDataSource,
    ) : LoadRepository {
        override suspend fun load() {
            val words: List<String> = cloudDataSource.load()
        }
    }

    class Fake : LoadRepository {
        private var count = 0
        override suspend fun load() {
            if (count == 0) {
                delay(1000)
                count++
                throw IllegalStateException()
            } else {
                delay(1000)
                count--
            }
        }
    }
}
