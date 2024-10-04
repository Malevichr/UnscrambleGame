package com.ru.malevich.unscramblegame

import android.app.Application
import com.ru.malevich.unscramblegame.di.ClearViewModel
import com.ru.malevich.unscramblegame.di.Core
import com.ru.malevich.unscramblegame.di.ManageViewModels
import com.ru.malevich.unscramblegame.di.MyViewModel
import com.ru.malevich.unscramblegame.di.ProvideViewModel

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

