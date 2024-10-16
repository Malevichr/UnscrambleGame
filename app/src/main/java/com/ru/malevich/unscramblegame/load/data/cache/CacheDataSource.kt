package com.ru.malevich.unscramblegame.load.data.cache

interface CacheDataSource {
    suspend fun save(words: List<String>)
    class Base(
        private val dao: WordsDao,
        private val clearDatabase: ClearDatabase
    ) : CacheDataSource {
        override suspend fun save(words: List<String>) {
            clearDatabase.clear()
            val wordsCache: List<WordCache> = words.mapIndexed { index, word ->
                WordCache(index, word)
            }
            dao.saveWords(wordsCache)
        }
    }
}
