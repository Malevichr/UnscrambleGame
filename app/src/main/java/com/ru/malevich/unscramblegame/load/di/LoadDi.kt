package com.ru.malevich.unscramblegame.load.di

import com.ru.malevich.unscramblegame.core.di.Core
import com.ru.malevich.unscramblegame.core.di.Module
import com.ru.malevich.unscramblegame.core.di.MyViewModel
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.load.data.LoadRepository
import com.ru.malevich.unscramblegame.load.data.cloud.CloudDataSource
import com.ru.malevich.unscramblegame.load.data.cloud.WordsService
import com.ru.malevich.unscramblegame.load.presentation.LoadUiObservable
import com.ru.malevich.unscramblegame.load.presentation.LoadViewModel
import com.ru.malevich.unscramblegame.load.presentation.RunAsync
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ProvideLoadViewModel(
    core: Core,
    nextLink: ProvideViewModel
) : ProvideViewModel.AbstractChainLink(
    core = core,
    nextLink = nextLink,
    LoadViewModel::class.java
) {
    override fun module(): Module<out MyViewModel> {
        return LoadModule(core)
    }
}

class LoadModule(private val core: Core) : Module<LoadViewModel> {
    override fun viewModel(): LoadViewModel {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://ao0ixd.buildship.run/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WordsService::class.java)

        return LoadViewModel(
            repository = if (core.runTest) {
                LoadRepository.Fake()
            } else
                LoadRepository.Base(
                cloudDataSource = CloudDataSource.Base(
                    service = service,
                    size = core.size
                )
            ),
            uiObservable = LoadUiObservable.Base(),
            runAsync = RunAsync.Base()
        )
    }
}