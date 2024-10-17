package com.ru.malevich.unscramblegame.load

import com.ru.malevich.unscramblegame.core.di.ClearViewModel
import com.ru.malevich.unscramblegame.core.presentation.MyViewModel
import com.ru.malevich.unscramblegame.core.presentation.UiState
import com.ru.malevich.unscramblegame.load.data.LoadRepository
import com.ru.malevich.unscramblegame.load.presentation.LoadUiObservable
import com.ru.malevich.unscramblegame.load.presentation.LoadUiState
import com.ru.malevich.unscramblegame.load.presentation.LoadViewModel
import com.ru.malevich.unscramblegame.load.presentation.RunAsync
import com.ru.malevich.unscramblegame.load.presentation.UiObservable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {
    private lateinit var viewModel: LoadViewModel
    private lateinit var repository: FakeLoadRepository
    private lateinit var observable: FakeLoadUiObservable
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setup() {
        repository = FakeLoadRepository()
        observable = FakeLoadUiObservable.Base()
        runAsync = FakeRunAsync()
        viewModel = LoadViewModel(
            repository = repository,
            uiObservable = observable,
            runAsync = runAsync,
            clearViewModel = object : ClearViewModel {
                override fun clear(viewModelClass: Class<out MyViewModel>) {}
            }
        )
    }

    @Test
    fun testSameFragment() = runBlocking {
        repository.expectedError()

        viewModel.load(isFirstRun = true)
        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Progress)

        val fragment = FakeFragment<LoadUiState>()
        viewModel.startUpdates(observer = fragment)
        repository.assertLoadCalledCount(1)
        fragment.assertCurrentState(LoadUiState.Progress)
        fragment.assertLoadStatesCount(1)

        runAsync.returnResult()

        repository.assertLoadCalledCount(1)
        fragment.assertCurrentState(LoadUiState.Error("error"))
        fragment.assertLoadStatesCount(2)

        repository.expectedSuccess()
        viewModel.load()

        repository.assertLoadCalledCount(2)
        fragment.assertCurrentState(LoadUiState.Progress)
        fragment.assertLoadStatesCount(3)

        runAsync.returnResult()

        repository.assertLoadCalledCount(2)
        fragment.assertCurrentState(LoadUiState.Success)
        fragment.assertLoadStatesCount(4)
    }

    @Test
    fun testAnotherFragment() = runBlocking {
        repository.expectedError()

        viewModel.load(isFirstRun = true)
        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Progress)

        val fragment = FakeFragment<LoadUiState>()
        viewModel.startUpdates(observer = fragment)

        repository.assertLoadCalledCount(1)
        fragment.assertCurrentState(LoadUiState.Progress)
        fragment.assertLoadStatesCount(1)

        viewModel.stopUpdates()

        runAsync.returnResult()

        repository.assertLoadCalledCount(1)
        fragment.assertCurrentState(LoadUiState.Progress)
        fragment.assertLoadStatesCount(1)

        val newInstanceFragment = FakeFragment<LoadUiState>()
        viewModel.load(isFirstRun = false)

        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Error("error"))

        viewModel.startUpdates(observer = newInstanceFragment)

        newInstanceFragment.assertCurrentState(LoadUiState.Error("error"))
        newInstanceFragment.assertLoadStatesCount(1)

        repository.expectedSuccess()
        viewModel.load()

        repository.assertLoadCalledCount(2)
        newInstanceFragment.assertCurrentState(LoadUiState.Progress)
        newInstanceFragment.assertLoadStatesCount(2)

        runAsync.returnResult()

        repository.assertLoadCalledCount(2)
        newInstanceFragment.assertCurrentState(LoadUiState.Success)
        newInstanceFragment.assertLoadStatesCount(3)
    }
}

@Suppress("UNCHECKED_CAST")
class FakeRunAsync : RunAsync {
    private lateinit var uiUpdateCached: (Any) -> Unit
    private lateinit var resultCached: Any
    override fun <T : Any> runAsync(
        coroutineScope: CoroutineScope,
        heavyOperation: suspend () -> T,
        uiUpdate: (T) -> Unit
    ) = runBlocking {
        resultCached = heavyOperation.invoke()
        uiUpdateCached = uiUpdate as (Any) -> Unit
    }

    fun returnResult() {
        uiUpdateCached.invoke(resultCached)
    }
}


class FakeFragment<T : UiState> : (T) -> Unit {
    private val states = mutableListOf<T>()
    override fun invoke(p1: T) {
        states.add(p1)
    }

    fun assertCurrentState(state: T) {
        assertEquals(state, states.last())
    }

    fun assertLoadStatesCount(count: Int) {
        assertEquals(count, states.size)
    }
}

interface FakeUiObservable<T : Any> : UiObservable<T> {
    fun assertCurrentState(state: T)
    fun assertRegisterCalledTimes(times: Int)
    fun assertUnregisterCalledTimes(times: Int)
    fun assertPostCalledTimes(times: Int)
    abstract class Abstract<T : Any> : UiObservable.Abstract<T>(), FakeUiObservable<T> {
        private var registerCalledCount: Int = 0
        private var unregisterCalledCount: Int = 0
        private var postCalledCount: Int = 0

        override fun assertUnregisterCalledTimes(times: Int) {
            assertEquals(times, unregisterCalledCount)
        }

        override fun assertRegisterCalledTimes(times: Int) {
            assertEquals(times, registerCalledCount)
        }

        override fun assertPostCalledTimes(times: Int) {
            assertEquals(times, postCalledCount)
        }

        private var uiStateCached: T? = null
        private var observerCached: ((T) -> Unit)? = null

        override fun postUiState(uiState: T) {
            postCalledCount++
            if (observerCached != null) {
                observerCached?.invoke(uiState)
                uiStateCached = null
            } else
                uiStateCached = uiState
        }

        override fun register(observer: (T) -> Unit) {
            registerCalledCount++
            observerCached = observer
            if (uiStateCached != null) {
                observerCached?.invoke(uiStateCached!!)
                uiStateCached = null
            }
        }

        override fun unregister() {
            unregisterCalledCount++
            this.observerCached = null
        }

        override fun assertCurrentState(state: T) {
            assertEquals(state, uiStateCached)
        }
    }
}

class FakeLoadRepository : LoadRepository {
    private var returnError: Boolean = false
    private var loadCalledCount = 0
    override suspend fun load() {
        loadCalledCount++
        if (returnError)
            throw IllegalStateException("error")
    }

    fun expectedError() {
        returnError = true
    }

    fun expectedSuccess() {
        returnError = false
    }

    fun assertLoadCalledCount(count: Int) {
        assertEquals(count, loadCalledCount)
    }
}

interface FakeLoadUiObservable : FakeUiObservable<LoadUiState>, LoadUiObservable {
    class Base : FakeUiObservable.Abstract<LoadUiState>(), FakeLoadUiObservable
}
