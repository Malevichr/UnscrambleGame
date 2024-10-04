package com.ru.malevich.unscramblegame.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ru.malevich.unscramblegame.R
import com.ru.malevich.unscramblegame.di.MyViewModel
import com.ru.malevich.unscramblegame.di.ProvideViewModel

class MainActivity : AppCompatActivity(), Navigate, ProvideViewModel {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            navigateToGame()
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }

    override fun <T : MyViewModel> provideViewModel(clazz: Class<T>): T {
        return (application as ProvideViewModel).provideViewModel(clazz)
    }
}