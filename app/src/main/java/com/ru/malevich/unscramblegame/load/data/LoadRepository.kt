package com.ru.malevich.unscramblegame.load.data

import com.ru.malevich.unscramblegame.load.data.cache.CacheDataSource
import com.ru.malevich.unscramblegame.load.data.cloud.CloudDataSource
import kotlinx.coroutines.delay

interface LoadRepository {

    suspend fun load()

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val cacheDataSource: CacheDataSource
    ) : LoadRepository {
        override suspend fun load() {
            val words: List<String> = cloudDataSource.load()
            cacheDataSource.save(words)
        }
    }

    class Fake : LoadRepository {
        private var count = 0
        override suspend fun load() {
            if (count == 0) {
                delay(2000)
                count++
                throw IllegalStateException()
            } else {
                delay(2000)
                count--
            }
        }
    }
}
