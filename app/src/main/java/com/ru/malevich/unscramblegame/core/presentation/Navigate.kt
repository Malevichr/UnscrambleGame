package com.ru.malevich.unscramblegame.core.presentation

import com.ru.malevich.unscramblegame.game.presentation.GameScreen
import com.ru.malevich.unscramblegame.game.presentation.NavigateToGame
import com.ru.malevich.unscramblegame.gameover.presentation.GameOverScreen
import com.ru.malevich.unscramblegame.gameover.presentation.NavigateToGameOver
import com.ru.malevich.unscramblegame.load.presentation.LoadScreen
import com.ru.malevich.unscramblegame.load.presentation.NavigateToLoad

interface Navigate : NavigateToGame, NavigateToGameOver, NavigateToLoad {
    fun navigate(screen: Screen)

    override fun navigateToGame() {
        navigate(GameScreen)
    }

    override fun navigateToGameOver() {
        navigate(GameOverScreen)
    }

    override fun navigateToLoad() {
        navigate(LoadScreen)
    }
}