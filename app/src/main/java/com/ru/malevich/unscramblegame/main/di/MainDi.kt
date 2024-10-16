package com.ru.malevich.unscramblegame.main.di

import com.ru.malevich.unscramblegame.core.data.IntCache
import com.ru.malevich.unscramblegame.core.di.Core
import com.ru.malevich.unscramblegame.core.di.Module
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.main.presentation.MainViewModel

class ProvideMainViewModel(
    core: Core,
    nextLink: ProvideViewModel
) : ProvideViewModel.AbstractChainLink(
    core,
    nextLink,
    MainViewModel::class.java
) {
    override fun module(): Module<out MyViewModel> {
        return MainModule(core)
    }

}

class MainModule(private val core: Core) : Module<MainViewModel> {
    override fun viewModel(): MainViewModel {
        val size = if (core.runTest)
            core.testSize
        else
            core.size
        return MainViewModel(
            index = IntCache.Base(core.sharedPreferences, "indexKey", size),
            size = size
        )
    }
}