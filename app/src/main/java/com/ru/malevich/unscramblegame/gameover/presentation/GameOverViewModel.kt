package com.ru.malevich.unscramblegame.gameover.presentation

import com.ru.malevich.unscramblegame.core.di.ClearViewModel
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.gameover.data.GameOverRepository
import com.ru.malevich.unscramblegame.views.statstextview.StatsUiState

class GameOverViewModel(
    private val repository: GameOverRepository,
    private val clearViewModel: ClearViewModel
) : MyViewModel {
    fun init(isFirstRun: Boolean): StatsUiState {
        if (isFirstRun) {
            val (corrects, incorrects) = repository.stats()
            return StatsUiState.Base(corrects, incorrects)
        } else
            return StatsUiState.Empty
    }

    fun clear() {
        clearViewModel.clear(GameOverViewModel::class.java)
        repository.clearStats()
    }
}
