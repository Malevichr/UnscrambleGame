package com.ru.malevich.unscramblegame.di

interface Module<T> {
    fun viewModel(): T
}
