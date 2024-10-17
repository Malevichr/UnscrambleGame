package com.ru.malevich.unscramblegame.gameover.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.core.presentation.AbstractFragment
import com.ru.malevich.unscramblegame.databinding.FragmentGameOverBinding
import com.ru.malevich.unscramblegame.load.presentation.NavigateToLoad
import com.ru.malevich.unscramblegame.views.statstextview.StatsUiState

class GameOverFragment : AbstractFragment.BindingUi<FragmentGameOverBinding>() {

    override fun inflate(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentGameOverBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: GameOverViewModel =
            (requireActivity() as ProvideViewModel).provideViewModel(GameOverViewModel::class.java)
        val uiState: StatsUiState = viewModel.init(savedInstanceState == null)
        binding.statsTextView.updateState(uiState)
        viewModel.clear()
        binding.newGameButton.setOnClickListener {
            (requireActivity() as NavigateToLoad).navigateToLoad()
        }
    }
}