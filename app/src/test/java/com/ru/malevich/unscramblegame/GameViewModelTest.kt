package com.ru.malevich.unscramblegame

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameViewModelTest {
    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel = GameViewModel(repository = FakeRepository())
    }

    /**
     * UGTC-01
     */
    @Test
    fun case1() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(scrambledWord = "auto".reversed())
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.assertInsufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("")
        expected = GameUiState.Initial(scrambledWord = "auto".reversed())
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auto")
        expected =
            GameUiState.SufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.check("auto")
        expected = GameUiState.RightAnswered(
            scrambledWord = "auto".reversed(),
            checkButtonState = ButtonUiState.RightAnsweredState()
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.InitialState(scrambledWord = "animal".reversed())
        assertEquals(expected, actual)
    }

    /**
     * UGTC-01
     */
    @Test
    fun case2() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(scrambledWord = "auto".reversed())
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.assertInsufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auau")
        expected =
            GameUiState.assertSufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.check("auau")
        expected = GameUiState.RightAnswered(
            scrambledWord = "auto".reversed(),
            checkButtonState = ButtonUiState.WrongAnsweredState()
        )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.assertInsufficientInput(
                scrambledWord = "au".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auto")
        expected =
            GameUiState.SufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.check("auto")
        expected = GameUiState.RightAnswered(
            scrambledWord = "auto".reversed(),
            checkButtonState = ButtonUiState.RightAnsweredState()
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.InitialState(scrambledWord = "animal".reversed())
        assertEquals(expected, actual)
    }

    /**
     * UGTC-03
     */
    @Test
    fun case3() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(scrambledWord = "auto".reversed())
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.assertInsufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.InitialState(scrambledWord = "animal".reversed())
        assertEquals(expected, actual)
    }

    /**
     * UGTC-04
     */
    @Test
    fun case4() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(scrambledWord = "auto".reversed())
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.assertInsufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auau")
        expected =
            GameUiState.assertSufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auauu")
        expected =
            GameUiState.assertInsufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auau")
        expected =
            GameUiState.assertSufficientInput(
                scrambledWord = "auto".reversed()
            )
        assertEquals(expected, actual)

        actual = viewModel.check("auau")
        expected = GameUiState.WrongAnswered(
            scrambledWord = "auto".reversed(),
            checkButtonState = ButtonUiState.WrongAnsweredState()
        )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.InitialState(scrambledWord = "animal".reversed())
        assertEquals(expected, actual)
    }
}

private class FakeRepository : GameRepository {
    private val list: List<Any> = listOf(
        UnscrambleTask(
            scrambledWord = "auto".reversed(),
            unscrambleWord = "auto"
        ),
        UnscrambleTask(
            scrambledWord = "animal".reversed(),
            unscrambleWord = "animal"
        ),
    )
    private var listIndex = 0
    override fun unscrambleTask(): UnscrumbleTask {
        return list[listIndex]
    }

    override fun next() {
        listIndex++
    }
}