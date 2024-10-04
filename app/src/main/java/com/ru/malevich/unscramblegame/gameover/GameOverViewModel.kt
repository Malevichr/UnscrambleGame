package com.ru.malevich.unscramblegame.gameover

import com.ru.malevich.unscramblegame.di.ClearViewModel
import com.ru.malevich.unscramblegame.di.MyViewModel
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
