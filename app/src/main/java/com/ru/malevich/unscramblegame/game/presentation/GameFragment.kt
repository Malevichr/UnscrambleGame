package com.ru.malevich.unscramblegame.game.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ru.malevich.unscramblegame.core.di.ProvideViewModel
import com.ru.malevich.unscramblegame.core.presentation.GameUiState
import com.ru.malevich.unscramblegame.databinding.FragmentGameBinding
import com.ru.malevich.unscramblegame.gameover.presentation.NavigateToGameOver

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: GameViewModel
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            viewModel.handleUserInput(text = binding.inputText.text.toString())
        }
    }
    private val update: (GameUiState) -> Unit = { uiState ->
        uiState.update(
            binding.scrambledWordTextView,
            binding.inputText,
            binding.checkButton,
            binding.nextButton,
            binding.skipButton
        )
        uiState.navigate(requireActivity() as NavigateToGameOver)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            (requireActivity() as ProvideViewModel).provideViewModel(GameViewModel::class.java)


        binding.nextButton.setOnClickListener {
            viewModel.next()
        }

        binding.checkButton.setOnClickListener {
            viewModel.check(text = binding.inputText.text.toString())
        }

        binding.skipButton.setOnClickListener {
            viewModel.skip()
        }

        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        binding.inputText.addTextChangedListener(textWatcher)
        viewModel.startUpdates(observer = update)
    }

    override fun onPause() {
        super.onPause()
        binding.inputText.removeTextChangedListener(textWatcher)
        viewModel.stopUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}