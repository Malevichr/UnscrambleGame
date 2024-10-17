package com.ru.malevich.unscramblegame.main.presentation

import com.ru.malevich.unscramblegame.core.data.IntCache
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.core.presentation.Screen
import com.ru.malevich.unscramblegame.game.presentation.GameScreen
import com.ru.malevich.unscramblegame.load.presentation.LoadScreen

class MainViewModel(
    private val index: IntCache,
    private val size: Int
) : MyViewModel {
    fun firstScreen(isFirstRun: Boolean): Screen {
        return if (isFirstRun) {
            if (index.read() == size)
                LoadScreen
            else
                GameScreen
        } else
            Screen.Empty
    }
}