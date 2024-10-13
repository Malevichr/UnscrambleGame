package com.ru.malevich.unscramblegame.load

import com.ru.malevich.unscramblegame.load.data.LoadRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LoadViewModelTest {
    private lateinit var viewModel: LoadViewModel
    private lateinit var repository: FakeLoadRepository
    private lateinit var observable: FakeLoadUiObservable

    @Before
    fun setup() {
        repository = FakeLoadRepository()
        observable = FakeLoadUiObservable.Base()
        viewModel = LoadViewModel(
            repository = repository,
            uiObservable = observable
        )
    }

    @Test
    fun testSameFragment() = runBlocking {
        repository.expectedError()

        viewModel.load(isFirstRun = true)
        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Progress)

        val fragment = FakeFragment()
        viewModel.startUpdates(observer = fragment)
        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Progress)
        fragment.assertLastState(LoadUiState.Progress)
        fragment.assertLoadStatesCount(1)

        repository.returnResult()

        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Error("error"))
        fragment.assertLastState(LoadUiState.Error("error"))
        fragment.assertLoadStatesCount(2)


        viewModel.load()

        repository.assertLoadCalledCount(2)
        observable.assertCurrentState(LoadUiState.Progress)
        fragment.assertLastState(LoadUiState.Progress)
        fragment.assertLoadStatesCount(3)

        repository.returnResult()

        repository.assertLoadCalledCount(2)
        observable.assertCurrentState(LoadUiState.Success)
        fragment.assertLastState(LoadUiState.Success)
        fragment.assertLoadStatesCount(4)
    }

    @Test
    fun testAnotherFragment() = runBlocking {
        repository.expectedError()

        viewModel.load(isFirstRun = true)
        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Progress)

        val fragment = FakeFragment()
        viewModel.startUpdates(observer = fragment)

        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Progress)
        fragment.assertLastState(LoadUiState.Progress)
        fragment.assertLoadStatesCount(1)

        viewModel.stopUpdates()

        repository.returnResult()

        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Error("error"))
        fragment.assertLastState(LoadUiState.Progress)
        fragment.assertLoadStatesCount(1)

        val newInstanceFragment = FakeFragment()
        viewModel.load(isFirstRun = false)

        repository.assertLoadCalledCount(1)
        observable.assertCurrentState(LoadUiState.Error("error"))

        viewModel.startUpdates(observer = fragment)

        newInstanceFragment.assertLastState(LoadUiState.Error("error"))
        newInstanceFragment.assertLoadStatesCount(1)

        viewModel.load()

        repository.assertLoadCalledCount(2)
        observable.assertCurrentState(LoadUiState.Progress)
        newInstanceFragment.assertLastState(LoadUiState.Progress)
        newInstanceFragment.assertLoadStatesCount(2)

        repository.returnResult()

        repository.assertLoadCalledCount(2)
        observable.assertCurrentState(LoadUiState.Success)
        fragment.assertLastState(LoadUiState.Success)
        fragment.assertLoadStatesCount(3)
    }
}


class FakeFragment : (LoadUiState) -> Unit {
    private val loadStates = mutableListOf<LoadUiState>()
    override fun invoke(p1: LoadUiState) {
        loadStates.add(p1)
    }

    fun assertLastState(state: LoadUiState) {
        assertEquals(state, loadStates.last())
    }

    fun assertLoadStatesCount(count: Int) {
        assertEquals(count, loadStates.size)
    }
}

interface FakeUiObservable<T : Any> : UiObservable<T> {
    fun assertCurrentState(state: LoadUiState)
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

        override fun assertCurrentState(state: LoadUiState) {
            assertEquals(state, uiStateCached)
        }
    }
}

class FakeLoadRepository : LoadRepository {
    private var returnLoadResult: Boolean = false
    private var returnError: Boolean = false
    private var loadCalledCount = 0
    override suspend fun load() {
        while (returnLoadResult) {
            if (returnError)
                throw IllegalStateException("error")
        }
    }

    fun expectedError() {
        returnError = true
    }

    fun expectedSuccess() {
        returnError = false
    }

    fun returnResult() {
        returnLoadResult = true
    }

    fun assertLoadCalledCount(count: Int) {
        assertEquals(loadCalledCount, count)
    }
}

interface FakeLoadUiObservable : FakeUiObservable<LoadUiState>, LoadUiObservable {
    class Base : FakeUiObservable.Abstract<LoadUiState>(), FakeLoadUiObservable
}
