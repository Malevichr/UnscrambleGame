package com.ru.malevich.unscramblegame

import android.app.Application
import android.content.Context
import com.ru.malevich.unscramblegame.data.BooleanCache
import com.ru.malevich.unscramblegame.data.IntCache
import com.ru.malevich.unscramblegame.data.StringCache
import com.ru.malevich.unscramblegame.game.GameRepository
import com.ru.malevich.unscramblegame.game.GameViewModel
import com.ru.malevich.unscramblegame.gameover.GameOverViewModel

class UGApp : AbstractApp()

interface ProvideViewModel {
    fun provideGameViewModel(): GameViewModel
    fun provideGameOverViewModel(): GameOverViewModel
}

abstract class AbstractApp : Application(), ProvideViewModel {
    protected lateinit var gameViewModel: GameViewModel
    private lateinit var gameOverViewModel: GameOverViewModel
    override fun onCreate() {
        super.onCreate()
        val sharedPreferences = getSharedPreferences("UgAppData", Context.MODE_PRIVATE)
        gameViewModel = GameViewModel(
            GameRepository.Base(
                IntCache.Base(sharedPreferences, "indexKey", 0),
                StringCache.Base(sharedPreferences, "inputKey", ""),
                BooleanCache.Base(sharedPreferences, "checkedKey", false)
            )
        )
    }

    override fun provideGameViewModel(): GameViewModel = gameViewModel
    override fun provideGameOverViewModel(): GameOverViewModel = gameOverViewModel
}

class FakeApp : AbstractApp() {
    fun resetViewModel() {
        val sharedPreferences = getSharedPreferences("UgAppData", Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
        gameViewModel = GameViewModel(
            GameRepository.Base(
                IntCache.Base(sharedPreferences, "indexKey", 0),
                StringCache.Base(sharedPreferences, "inputKey", ""),
                BooleanCache.Base(sharedPreferences, "checkedKey", false)
            )
        )
    }
}