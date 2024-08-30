package com.ru.malevich.unscramblegame

import android.app.Application
import android.content.Context
import com.ru.malevich.unscramblegame.data.BooleanCache
import com.ru.malevich.unscramblegame.data.GameRepository
import com.ru.malevich.unscramblegame.data.IntCache
import com.ru.malevich.unscramblegame.data.StringCache

class UGApp : AbstractApp()

interface ProvideViewModel {
    fun provideViewModel(): GameViewModel
}

abstract class AbstractApp : Application(), ProvideViewModel {
    protected lateinit var viewModel: GameViewModel
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("UgAppData", Context.MODE_PRIVATE)
        viewModel = GameViewModel(
            GameRepository.Base(
                IntCache.Base(sharedPreferences, "indexKey", 0),
                StringCache.Base(sharedPreferences, "inputKey", ""),
                BooleanCache.Base(sharedPreferences, "checkedKey", false)
            )
        )
    }

    override fun provideViewModel(): GameViewModel = viewModel
}

class FakeApp : AbstractApp() {
    fun resetViewModel() {
        val sharedPreferences = getSharedPreferences("UgAppData", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        viewModel = GameViewModel(
            GameRepository.Base(
                IntCache.Base(sharedPreferences, "indexKey", 0),
                StringCache.Base(sharedPreferences, "inputKey", ""),
                BooleanCache.Base(sharedPreferences, "checkedKey", false)
            )
        )
    }
}