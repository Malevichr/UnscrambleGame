package com.ru.malevich.unscramblegame.view

import com.ru.malevich.unscramblegame.databinding.ActivityMainBinding
import java.io.Serializable

interface UiState : Serializable {
    fun update(binding: ActivityMainBinding)
}