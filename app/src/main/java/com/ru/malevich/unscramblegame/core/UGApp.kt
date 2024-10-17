package com.ru.malevich.unscramblegame.core

import android.app.Application
import com.ru.malevich.unscramblegame.core.di.ClearViewModel
import com.ru.malevich.unscramblegame.core.di.Core
import com.ru.malevich.unscramblegame.core.di.ManageViewModels
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel

class UGApp : Application(), ProvideViewModel {
    private lateinit var viewModelFactory: ManageViewModels
    override fun onCreate() {
        super.onCreate()
        val clearViewModel = object : ClearViewModel {
            override fun clear(viewModelClass: Class<out MyViewModel>) {
                viewModelFactory.clear(viewModelClass)
            }
        }
        val core = Core(this, clearViewModel)
        val make = ProvideViewModel.Make(core)
        viewModelFactory = ManageViewModels.Factory(make)
    }

    override fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T {
        return viewModelFactory.provideViewModel(clazz)
    }
}

