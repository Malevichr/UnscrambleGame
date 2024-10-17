package com.ru.malevich.unscramblegame.load.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.core.presentation.AbstractFragment
import com.ru.malevich.unscramblegame.databinding.FragmentLoadBinding
import com.ru.malevich.unscramblegame.game.presentation.NavigateToGame

class LoadFragment : AbstractFragment.Async<LoadUiState, LoadViewModel, FragmentLoadBinding>() {
    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentLoadBinding.inflate(inflater, container, false)

    override val update: (LoadUiState) -> Unit = { uiState: LoadUiState ->
        uiState.update(
            binding.progressBar,
            binding.errorTextView,
            binding.retryButton
        )
        uiState.navigate(requireActivity() as NavigateToGame)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            (requireActivity() as ProvideViewModel).provideViewModel(LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.load()
        }

        viewModel.load(isFirstRun = savedInstanceState == null)
    }
}