package com.ru.malevich.unscramblegame.core.di

import com.ru.malevich.unscramblegame.game.di.ProvideGameViewModel
import com.ru.malevich.unscramblegame.gameover.di.ProvideGameOverViewModel
import com.ru.malevich.unscramblegame.load.di.ProvideLoadViewModel

interface ProvideViewModel {
    fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T
    class Make(
        core: Core
    ) : ProvideViewModel {
        private var chain: ProvideViewModel

        init {
            chain = Error()
            chain = ProvideGameViewModel(core, chain)
            chain = ProvideGameOverViewModel(core, chain)
            chain = ProvideLoadViewModel(core, chain)
        }

        override fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T =
            chain.provideViewModel(clazz)

    }

    class Error : ProvideViewModel {
        override fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T {
            throw IllegalStateException("Unknown class: $clazz")
        }
    }

    abstract class AbstractChainLink(
        protected val core: Core,
        private val nextLink: ProvideViewModel,
        private val viewModelClass: Class<out MyViewModel>
    ) : ProvideViewModel {
        override fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T {
            return if (viewModelClass == clazz)
                module().viewModel() as T
            else
                nextLink.provideViewModel(clazz)
        }

        protected abstract fun module(): Module<out MyViewModel>
    }
}

interface MyViewModel