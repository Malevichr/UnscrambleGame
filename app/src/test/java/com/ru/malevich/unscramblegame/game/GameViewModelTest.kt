package com.ru.malevich.unscramblegame.game

import com.ru.malevich.unscramblegame.views.GameUiState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class GameViewModelTest {
    private lateinit var viewModel: GameViewModel
    private lateinit var scrambledWord: String

    @Before
    fun setup() {
        viewModel = GameViewModel(repository = FakeRepository())
        scrambledWord = "auto".reversed()
    }

    /**
     * UGTC-01
     */
    @Test
    fun case1() {
        var actual: GameUiState = viewModel.init()

        var expected: GameUiState = GameUiState.Initial(scrambledWord = scrambledWord)
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected = GameUiState.InsufficientInput(scrambledWord, "au")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("")
        expected = GameUiState.InsufficientInput(scrambledWord, "")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected = GameUiState.InsufficientInput(scrambledWord, "au")
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auto")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auto"
            )
        assertEquals(expected, actual)

        actual = viewModel.check("auto")
        expected = GameUiState.RightAnswered(scrambledWord, "auto")
        assertEquals(expected, actual)

        actual = viewModel.next()
        scrambledWord = "animal".reversed()
        expected = GameUiState.Initial(scrambledWord = scrambledWord)
        assertEquals(expected, actual)
    }

    /**
     * UGTC-01
     */
    @Test
    fun case2() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(scrambledWord = scrambledWord)
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "au"
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auau")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auau"
            )
        assertEquals(expected, actual)

        actual = viewModel.check("auau")
        expected = GameUiState.WrongAnswered(
            scrambledWord = scrambledWord,
            "auau"
        )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "au"
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auto")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auto"
            )
        assertEquals(expected, actual)

        actual = viewModel.check("auto")
        expected = GameUiState.RightAnswered(scrambledWord = scrambledWord, "auto")
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.Initial(scrambledWord = "animal".reversed())
        assertEquals(expected, actual)
        actual = viewModel.next()
        expected = GameUiState.Initial(scrambledWord = "car".reversed())
        assertEquals(expected, actual)
    }

    /**
     * UGTC-03
     */
    @Test
    fun case3() {
        var actual: GameUiState = viewModel.init()
        var expected: GameUiState = GameUiState.Initial(scrambledWord = scrambledWord)
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("au")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "au"
            )
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.Initial(scrambledWord = "animal".reversed())
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
            GameUiState.InsufficientInput(
                scrambledWord,
                "au"
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auau")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auau"
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auauu")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "auauu"
            )
        assertEquals(expected, actual)

        actual = viewModel.handleUserInput("auau")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auau"
            )
        assertEquals(expected, actual)

        actual = viewModel.check("auau")
        expected = GameUiState.WrongAnswered(scrambledWord = scrambledWord, "auau")
        assertEquals(expected, actual)

        actual = viewModel.next()
        expected = GameUiState.Initial(scrambledWord = "animal".reversed())
        assertEquals(expected, actual)
    }
}

private class FakeRepository : GameRepository {
    private val list: List<String> = listOf(
        "auto",
        "animal"
    )
    private var listIndex = 0
    override fun unscrambleTask(): UnscrambleTask {
        val word: String = list[listIndex]
        return UnscrambleTask(unscrambledWord = word, scrambledWord = word.reversed())
    }
    private var savedText = ""
    override fun saveUserInput(input: String) {
        savedText = input
    }

    override fun userInput() = savedText
    private var checked = false
    override fun saveChecked(boolean: Boolean) {
        checked = boolean
    }

    override fun isChecked(): Boolean = checked
    override fun next() {
        listIndex++
        savedText = ""
    }

    override fun clearProgress() {
        TODO("Not yet implemented")
    }

    override fun incCorrects() {
        TODO("Not yet implemented")
    }

    override fun incIncorrects() {
        TODO("Not yet implemented")
    }

    override fun isLastQuestion(): Boolean {
        TODO("Not yet implemented")
    }
}