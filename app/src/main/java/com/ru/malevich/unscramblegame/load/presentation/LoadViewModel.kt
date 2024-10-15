package com.ru.malevich.unscramblegame.load.presentation

import com.ru.malevich.unscramblegame.core.di.MyViewModel
import com.ru.malevich.unscramblegame.load.data.LoadRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class LoadViewModel(
    private val repository: LoadRepository,
    private val uiObservable: LoadUiObservable,
    private val runAsync: RunAsync
) : MyViewModel {

    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    fun load(isFirstRun: Boolean = true) {
        if (isFirstRun) {
            uiObservable.postUiState(LoadUiState.Progress)
            runAsync.runAsync(
                coroutineScope = viewModelScope,
                heavyOperation = {
                    try {
                        repository.load()
                        LoadUiState.Success
                    } catch (e: Exception) {
                        LoadUiState.Error(e.message ?: "error")
                    }
                },
                uiUpdate = {
                    uiObservable.postUiState(it)
                }
            )
        }
    }

    fun startUpdates(observer: (LoadUiState) -> Unit) {
        uiObservable.register(observer)

    }

    fun stopUpdates() {
        uiObservable.unregister()
    }
}