package com.ru.malevich.unscramblegame.gameover

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
        viewModel = GameOverViewModel(repository)
    }

    @Test
    fun case1() {
        val actual: StatsUiState = viewModel.statsUiState()
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