package com.ru.malevich.unscramblegame.game

import com.ru.malevich.unscramblegame.core.di.ClearViewModel
import com.ru.malevich.unscramblegame.core.presentation.GameUiState
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.game.data.GameRepository
import com.ru.malevich.unscramblegame.game.data.UnscrambleTask
import com.ru.malevich.unscramblegame.game.presentation.GameUiObservable
import com.ru.malevich.unscramblegame.game.presentation.GameViewModel
import com.ru.malevich.unscramblegame.load.FakeFragment
import com.ru.malevich.unscramblegame.load.FakeRunAsync
import com.ru.malevich.unscramblegame.load.FakeUiObservable
import kotlinx.coroutines.runBlocking
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
    private lateinit var runAsync: FakeRunAsync
    private lateinit var observable: FakeGameUiObservable
    private lateinit var fakeFragment: FakeFragment<GameUiState>
    @Before
    fun setup() {
        fakeFragment = FakeFragment()
        observable = FakeGameUiObservable.Base()
        runAsync = FakeRunAsync()
        viewModel = GameViewModel(
            repository = FakeRepository(),
            clearViewModel = object : ClearViewModel {
                override fun clear(viewModelClass: Class<out MyViewModel>) = Unit
            },
            runAsync = runAsync,
            observable = observable
        )
        scrambledWord = "auto".reversed()
    }

    private fun assertAsyncResult(gameUiState: GameUiState) {
        runAsync.returnResult()
        return fakeFragment.assertCurrentState(gameUiState)
    }
    /**
     * UGTC-01
     */
    @Test
    fun case1() = runBlocking {
        viewModel.init()
        viewModel.startUpdates(fakeFragment)
        var expected: GameUiState = GameUiState.Initial(scrambledWord = scrambledWord)
        assertAsyncResult(expected)

        viewModel.handleUserInput("au")
        expected = GameUiState.InsufficientInput(scrambledWord, "au")
        assertAsyncResult(expected)

        viewModel.handleUserInput("")
        expected = GameUiState.InsufficientInput(scrambledWord, "")
        assertAsyncResult(expected)

        viewModel.handleUserInput("au")
        expected = GameUiState.InsufficientInput(scrambledWord, "au")
        assertAsyncResult(expected)

        viewModel.handleUserInput("auto")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auto"
            )
        assertAsyncResult(expected)

        viewModel.check("auto")
        expected = GameUiState.RightAnswered(scrambledWord, "auto")
        assertAsyncResult(expected)

        viewModel.next()
        scrambledWord = "animal".reversed()
        expected = GameUiState.Initial(scrambledWord = scrambledWord)
        assertAsyncResult(expected)
    }

    /**
     * UGTC-01
     */
    @Test
    fun case2() {
        viewModel.init()
        viewModel.startUpdates(fakeFragment)
        var expected: GameUiState = GameUiState.Initial(scrambledWord = scrambledWord)
        assertAsyncResult(expected)

        viewModel.handleUserInput("au")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "au"
            )
        assertAsyncResult(expected)

        viewModel.handleUserInput("auau")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auau"
            )
        assertAsyncResult(expected)

        viewModel.check("auau")
        expected = GameUiState.WrongAnswered(
            scrambledWord = scrambledWord,
            "auau"
        )
        assertAsyncResult(expected)

        viewModel.handleUserInput("au")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "au"
            )
        assertAsyncResult(expected)

        viewModel.handleUserInput("auto")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auto"
            )
        assertAsyncResult(expected)

        viewModel.check("auto")
        expected = GameUiState.RightAnswered(scrambledWord = scrambledWord, "auto")
        assertAsyncResult(expected)

        viewModel.next()
        expected = GameUiState.Initial(scrambledWord = "animal".reversed())
        assertAsyncResult(expected)
    }

    /**
     * UGTC-03
     */
    @Test
    fun case3() {
        viewModel.init()
        viewModel.startUpdates(fakeFragment)
        var expected: GameUiState = GameUiState.Initial(scrambledWord = scrambledWord)
        assertAsyncResult(expected)

        viewModel.handleUserInput("au")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "au"
            )
        assertAsyncResult(expected)

        viewModel.next()
        expected = GameUiState.Initial(scrambledWord = "animal".reversed())
        assertAsyncResult(expected)
    }

    /**
     * UGTC-04
     */
    @Test
    fun case4() {
        viewModel.init()
        viewModel.startUpdates(fakeFragment)
        var expected: GameUiState = GameUiState.Initial(scrambledWord = "auto".reversed())
        assertAsyncResult(expected)

        viewModel.handleUserInput("au")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "au"
            )
        assertAsyncResult(expected)

        viewModel.handleUserInput("auau")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auau"
            )
        assertAsyncResult(expected)

        viewModel.handleUserInput("auauu")
        expected =
            GameUiState.InsufficientInput(
                scrambledWord,
                "auauu"
            )
        assertAsyncResult(expected)

        viewModel.handleUserInput("auau")
        expected =
            GameUiState.SufficientInput(
                scrambledWord,
                "auau"
            )
        assertAsyncResult(expected)

        viewModel.check("auau")
        expected = GameUiState.WrongAnswered(scrambledWord = scrambledWord, "auau")
        assertAsyncResult(expected)

        viewModel.next()
        expected = GameUiState.Initial(scrambledWord = "animal".reversed())
        assertAsyncResult(expected)
    }
}

private class FakeRepository : GameRepository {
    private val list: List<String> = listOf(
        "auto",
        "animal"
    )
    private var listIndex = 0
    override suspend fun unscrambleTask(): UnscrambleTask {
        val word: String = list[listIndex]
        return UnscrambleTask(unscrambledWord = word, scrambledWord = word.reversed())
    }
    private var savedText = ""
    override fun saveUserInput(input: String) {
        savedText = input
    }

    override fun userInput() = savedText
    override fun next() {
        listIndex++
        savedText = ""
    }

    override fun clearProgress() {
        listIndex = 0
    }

    override fun incCorrects() {
    }

    override fun incIncorrects() {
    }

    override fun isLastQuestion(): Boolean = listIndex + 1 == list.size
}

private interface FakeGameUiObservable : FakeUiObservable<GameUiState>, GameUiObservable {
    class Base : FakeUiObservable.Abstract<GameUiState>(), FakeGameUiObservable
}