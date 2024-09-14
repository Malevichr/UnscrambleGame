package com.ru.malevich.unscramblegame.views

import com.ru.malevich.unscramblegame.game.GameScreen
import com.ru.malevich.unscramblegame.game.NavigateToGame
import com.ru.malevich.unscramblegame.gameover.GameOverScreen
import com.ru.malevich.unscramblegame.gameover.NavigateToGameOver

interface Navigate : NavigateToGame, NavigateToGameOver {
    fun navigate(screen: Screen)

    override fun navigateToGame() {
        navigate(GameScreen)
    }

    override fun navigateToGameOver() {
        navigate(GameOverScreen)
    }
}