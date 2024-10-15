package com.ru.malevich.unscramblegame.di

import android.content.Context

class Core(
    context: Context,
    val clearViewModel: ClearViewModel
) {
    val sharedPreferences = context.getSharedPreferences("ugAppData", Context.MODE_PRIVATE)
    val size = 5
}