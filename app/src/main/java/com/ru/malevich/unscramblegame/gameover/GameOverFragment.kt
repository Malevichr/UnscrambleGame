package com.ru.malevich.unscramblegame.gameover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ru.malevich.unscramblegame.ProvideViewModel
import com.ru.malevich.unscramblegame.databinding.FragmentGameOverBinding
import com.ru.malevich.unscramblegame.game.NavigateToGame
import com.ru.malevich.unscramblegame.views.statstextview.StatsUiState

class GameOverFragment : Fragment() {
    private var _binding: FragmentGameOverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameOverBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: GameOverViewModel =
            (requireActivity().application as ProvideViewModel).provideGameOverViewModel()
        val uiState: StatsUiState = viewModel.statsUiState()
        binding.statsTextView.updateState(uiState)

        binding.newGameButton.setOnClickListener {
            viewModel.clear()
            (requireActivity() as NavigateToGame).navigateToGame()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}