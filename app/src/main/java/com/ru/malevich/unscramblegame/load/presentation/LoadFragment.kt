package com.ru.malevich.unscramblegame.load.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.databinding.FragmentLoadBinding
import com.ru.malevich.unscramblegame.game.presentation.NavigateToGame

class LoadFragment : Fragment() {
    private var _binding: FragmentLoadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private lateinit var viewModel: LoadViewModel

    private val update: (LoadUiState) -> Unit = { uiState: LoadUiState ->
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

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}