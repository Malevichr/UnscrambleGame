package com.ru.malevich.unscramblegame.gameover

import com.ru.malevich.unscramblegame.data.IntCache
import com.ru.malevich.unscramblegame.di.Core
import com.ru.malevich.unscramblegame.di.Module
import com.ru.malevich.unscramblegame.di.MyViewModel
import com.ru.malevich.unscramblegame.di.ProvideViewModel

class ProvideGameOverViewModel(
    core: Core,
    nextLink: ProvideViewModel
) : ProvideViewModel.AbstractChainLink(
    core,
    nextLink,
    GameOverViewModel::class.java
) {
    override fun module(): Module<out MyViewModel> = GameOverModule(core)
}

class GameOverModule(private val core: Core) : Module<GameOverViewModel> {
    override fun viewModel(): GameOverViewModel {
        val sharedPreferences = core.sharedPreferences
        return GameOverViewModel(
            GameOverRepository.Base(
                IntCache.Base(sharedPreferences, "corrects", 0),
                IntCache.Base(sharedPreferences, "incorrects", 0)
            ),
            core.clearViewModel
        )
    }
}