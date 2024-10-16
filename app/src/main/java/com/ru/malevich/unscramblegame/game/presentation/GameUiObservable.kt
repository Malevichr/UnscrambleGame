package com.ru.malevich.unscramblegame.game.presentation

import com.ru.malevich.unscramblegame.core.presentation.GameUiState
import com.ru.malevich.unscramblegame.load.presentation.UiObservable

interface GameUiObservable : UiObservable<GameUiState> {
    class Base : UiObservable.Abstract<GameUiState>(), GameUiObservable
}
