package com.ru.malevich.unscramblegame.gameover

class GameOverViewModel(
    private val repository: GameOverRepository
) {
    fun statsUiState(): StatsUiState {
        val (corrects, incorrects) = repository.stats()
        return StatsUiState.Base(corrects, incorrects)
    }

}
