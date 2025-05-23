package com.ru.malevich.unscramblegame.game.di

import com.ru.malevich.unscramblegame.core.data.IntCache
import com.ru.malevich.unscramblegame.core.data.StringCache
import com.ru.malevich.unscramblegame.core.di.Core
import com.ru.malevich.unscramblegame.core.di.Module
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.game.data.GameRepository
import com.ru.malevich.unscramblegame.game.data.ShuffleWord
import com.ru.malevich.unscramblegame.game.presentation.GameUiObservable
import com.ru.malevich.unscramblegame.game.presentation.GameViewModel

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
            if (core.runTest)
                GameRepository.Fake(
                    listIndex = IntCache.Base(sharedPreferences, "indexKey", core.testSize),
                    userInputText = StringCache.Base(sharedPreferences, "inputKey", ""),
                    corrects = IntCache.Base(sharedPreferences, "corrects", 0),
                    incorrects = IntCache.Base(sharedPreferences, "incorrects", 0),
                    shuffleWord = ShuffleWord.Reverse(),
                )
            else
                GameRepository.Base(
                    index = IntCache.Base(sharedPreferences, "indexKey", core.size),
                    userInputText = StringCache.Base(sharedPreferences, "inputKey", ""),
                    corrects = IntCache.Base(sharedPreferences, "corrects", 0),
                    incorrects = IntCache.Base(sharedPreferences, "incorrects", 0),
                    shuffleWord = ShuffleWord.Reverse(),
                    dao = core.cacheModule.dao(),
                    size = core.size
                ),
            clearViewModel = core.clearViewModel,
            runAsync = core.runAsync,
            observable = GameUiObservable.Base()
        )
    }
}