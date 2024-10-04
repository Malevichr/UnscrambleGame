package com.ru.malevich.unscramblegame.di

import com.ru.malevich.unscramblegame.game.ProvideGameViewModel
import com.ru.malevich.unscramblegame.gameover.ProvideGameOverViewModel

interface ProvideViewModel {
    fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T
    class Make(
        private val core: Core
    ) : ProvideViewModel {
        private var chain: ProvideViewModel

        init {
            chain = Error()
            chain = ProvideGameViewModel(core, chain)
            chain = ProvideGameOverViewModel(core, chain)
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
        private val nextLinl: ProvideViewModel,
        private val viewModelClass: Class<out MyViewModel>
    ) : ProvideViewModel {
        override fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T {
            return if (viewModelClass == clazz)
                module().viewModel() as T
            else
                nextLinl.provideViewModel(clazz)
        }

        protected abstract fun module(): Module<out MyViewModel>
    }
}

interface MyViewModel