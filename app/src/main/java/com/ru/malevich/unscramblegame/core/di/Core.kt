package com.ru.malevich.unscramblegame.core.di

import android.content.Context
import android.content.SharedPreferences
import com.ru.malevich.unscramblegame.load.data.cache.CacheModule
import com.ru.malevich.unscramblegame.load.presentation.RunAsync

class Core(
    context: Context,
    val clearViewModel: ClearViewModel
) {
    val runAsync: RunAsync = RunAsync.Base()
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ugAppData", Context.MODE_PRIVATE)
    val size = 2
    val testSize = 2
    val runTest: Boolean = true
    val cacheModule: CacheModule = CacheModule.Base(context)
}