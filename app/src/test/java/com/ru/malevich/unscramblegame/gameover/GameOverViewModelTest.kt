package com.ru.malevich.unscramblegame.gameover

import com.ru.malevich.unscramblegame.core.di.ClearViewModel
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.gameover.data.GameOverRepository
import com.ru.malevich.unscramblegame.gameover.presentation.GameOverViewModel
import com.ru.malevich.unscramblegame.views.statstextview.StatsUiState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class GameOverViewModelTest {
    private lateinit var repository: GameOverRepository
    private lateinit var viewModel: GameOverViewModel

    @Before
    fun setup() {
        repository = FakeRepository(0, 2)
        viewModel = GameOverViewModel(repository, object : ClearViewModel {
            override fun clear(viewModelClass: Class<out MyViewModel>) {
                TODO("Not yet implemented")
            }
        })
    }

    @Test
    fun case1() {
        val actual: StatsUiState = viewModel.init(true)
        val expected: StatsUiState = StatsUiState.Base(corrects = 0, incorrects = 2)
        assertEquals(expected, actual)
    }
}

private class FakeRepository(
    private val corrects: Int,
    private val incorrects: Int
) :
    GameOverRepository {
    override fun stats(): Pair<Int, Int> {
        return Pair(corrects, incorrects)
    }

    override fun clearStats() {
        TODO("Not yet implemented")
    }
}