package com.ru.malevich.unscramblegame.load.presentation

import com.ru.malevich.unscramblegame.core.di.ClearViewModel
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.load.data.LoadRepository

class LoadViewModel(
    private val repository: LoadRepository,
    private val clearViewModel: ClearViewModel,
    uiObservable: LoadUiObservable,
    runAsync: RunAsync
) : MyViewModel.Async.Abstract<LoadUiState>(runAsync, uiObservable) {

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            observable.postUiState(LoadUiState.Progress)
            handleAsync {
                    try {
                        repository.load()
                        clearViewModel.clear(this::class.java)
                        LoadUiState.Success
                    } catch (e: Exception) {
                        LoadUiState.Error(e.message ?: "error")
                    }
            }
        }
    }
}