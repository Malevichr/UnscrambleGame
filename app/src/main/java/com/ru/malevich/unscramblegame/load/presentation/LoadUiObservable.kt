package com.ru.malevich.unscramblegame.load.presentation

interface LoadUiObservable : UiObservable<LoadUiState> {
    class Base : UiObservable.Abstract<LoadUiState>(), LoadUiObservable
}