package com.ru.malevich.unscramblegame

import com.ru.malevich.unscramblegame.databinding.ActivityMainBinding

interface UiState {
    fun update(binding: ActivityMainBinding)
}