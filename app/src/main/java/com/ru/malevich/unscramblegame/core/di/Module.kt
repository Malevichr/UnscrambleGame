package com.ru.malevich.unscramblegame.core.di

interface Module<T> {
    fun viewModel(): T
}