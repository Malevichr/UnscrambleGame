package com.ru.malevich.unscramblegame.load.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("Words")
class WordCache(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("word")
    val word: String
)