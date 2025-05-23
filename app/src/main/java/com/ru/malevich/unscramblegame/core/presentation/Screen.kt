package com.ru.malevich.unscramblegame.core.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface Screen {
    object Empty : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) {
        }

    }

    fun show(containerId: Int, fragmentManager: FragmentManager)

    abstract class Replace(private val fragment: Class<out Fragment>) : Screen {
        override fun show(containerId: Int, fragmentManager: FragmentManager) {
            fragmentManager.beginTransaction()
                .replace(containerId, makeFragment())
                .commit()
        }

        protected open fun makeFragment(): Fragment =
            fragment.getDeclaredConstructor().newInstance()
    }
}