package com.ru.malevich.unscramblegame.core.di

import com.ru.malevich.unscramblegame.core.presentation.MyViewModel

interface ClearViewModel {
    fun clear(viewModelClass: Class<out MyViewModel>)
}