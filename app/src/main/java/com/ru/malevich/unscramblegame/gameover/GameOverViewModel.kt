package com.ru.malevich.unscramblegame.gameover

import com.ru.malevich.unscramblegame.views.statstextview.StatsUiState

class GameOverViewModel(
    private val repository: GameOverRepository
) {
    fun statsUiState(): StatsUiState {
        val (corrects, incorrects) = repository.stats()
        return StatsUiState.Base(corrects, incorrects)
    }
    fun clear() {
        repository.clearStats()
    }
}
