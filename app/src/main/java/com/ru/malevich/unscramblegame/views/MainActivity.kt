package com.ru.malevich.unscramblegame.views

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ru.malevich.unscramblegame.GameViewModel
import com.ru.malevich.unscramblegame.ProvideViewModel
import com.ru.malevich.unscramblegame.R
import com.ru.malevich.unscramblegame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var uiState: GameUiState
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: ActivityMainBinding
    private val update: () -> Unit = {
        uiState.update(
            binding.scrambledWordTextView,
            binding.inputText,
            binding.checkButton,
            binding.nextButton,
            binding.skipButton
        )
    }
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(s: Editable?) {
            uiState = viewModel.handleUserInput(text = binding.inputText.text.toString())
            update.invoke()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.rootLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = (application as ProvideViewModel).provideViewModel()

        binding.nextButton.setOnClickListener {
            uiState = viewModel.next()
            update.invoke()
        }

        binding.checkButton.setOnClickListener {
            uiState = viewModel.check(text = binding.inputText.text.toString())
            update.invoke()
        }

        binding.skipButton.setOnClickListener {
            uiState = viewModel.next()
            update.invoke()
        }

        uiState = viewModel.init(savedInstanceState == null)
        update.invoke()
    }
    override fun onResume() {
        super.onResume()
        binding.inputText.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.inputText.removeTextChangedListener(textWatcher)
    }
}