package com.ru.malevich.unscramblegame.load.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordsDao {
    @Insert
    suspend fun saveWords(words: List<WordCache>)

    @Query("SELECT * FROM Words WHERE id = :id")
    suspend fun word(id: Int): WordCache
}