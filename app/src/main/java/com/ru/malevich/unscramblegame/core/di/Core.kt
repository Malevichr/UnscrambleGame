package com.ru.malevich.unscramblegame.core.di

import android.content.Context
import android.content.SharedPreferences

class Core(
    context: Context,
    val clearViewModel: ClearViewModel
) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("ugAppData", Context.MODE_PRIVATE)
    val size = 5
    val runTest: Boolean = true
}