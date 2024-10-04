package com.ru.malevich.unscramblegame.game

import com.ru.malevich.unscramblegame.data.BooleanCache
import com.ru.malevich.unscramblegame.data.IntCache
import com.ru.malevich.unscramblegame.data.StringCache
import com.ru.malevich.unscramblegame.di.Core
import com.ru.malevich.unscramblegame.di.Module
import com.ru.malevich.unscramblegame.di.MyViewModel
import com.ru.malevich.unscramblegame.di.ProvideViewModel

class ProvideGameViewModel(
    core: Core,
    nextChainLink: ProvideViewModel
) : ProvideViewModel.AbstractChainLink(
    core,
    nextChainLink,
    GameViewModel::class.java
) {
    override fun module(): Module<out MyViewModel> =
        GameModule(core)
}

class GameModule(private val core: Core) : Module<GameViewModel> {
    override fun viewModel(): GameViewModel {
        val sharedPreferences = core.sharedPreferences
        return GameViewModel(
            GameRepository.Base(
                IntCache.Base(sharedPreferences, "indexKey", 0),
                StringCache.Base(sharedPreferences, "inputKey", ""),
                BooleanCache.Base(sharedPreferences, "checkedKey", false),
                IntCache.Base(sharedPreferences, "corrects", 0),
                IntCache.Base(sharedPreferences, "incorrects", 0)
            ),
            clearViewModel = core.clearViewModel
        )
    }
}