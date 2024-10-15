package com.ru.malevich.unscramblegame.main.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ru.malevich.unscramblegame.R
import com.ru.malevich.unscramblegame.core.di.MyViewModel
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.core.presentation.Navigate
import com.ru.malevich.unscramblegame.core.presentation.Screen

class MainActivity : AppCompatActivity(), Navigate, ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            navigateToLoad()
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }

    override fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T {
        return (application as ProvideViewModel).provideViewModel(clazz)
    }
}