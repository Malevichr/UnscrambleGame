package com.ru.malevich.unscramblegame.load

interface LoadUiObservable : UiObservable<LoadUiState> {
    class Base : UiObservable.Abstract<LoadUiState>(), LoadUiObservable
}