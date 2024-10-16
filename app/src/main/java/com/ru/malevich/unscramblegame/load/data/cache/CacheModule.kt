package com.ru.malevich.unscramblegame.load.data.cache

import android.content.Context
import androidx.room.Room

interface CacheModule {
    fun dao(): WordsDao
    fun clearDatabase(): ClearDatabase
    class Base(applicationContext: Context) : CacheModule {
        private val database by lazy {
            Room.databaseBuilder(
                applicationContext,
                WordsDataBase::class.java,
                "words-database"
            ).build()
        }

        override fun dao(): WordsDao =
            database.dao()

        override fun clearDatabase(): ClearDatabase =
            database
    }
}