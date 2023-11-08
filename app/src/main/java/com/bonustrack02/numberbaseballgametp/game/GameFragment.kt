package com.bonustrack02.numberbaseballgametp.game

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bonustrack02.numberbaseballgametp.R
import com.bonustrack02.numberbaseballgametp.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var safeContext: Context
    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        safeContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        viewModel = ViewModelProvider(this)[GameViewModel::class.java]
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.hideKeyboardEvent.observe(viewLifecycleOwner) {
            val inputMethodManager = safeContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
        }
        viewModel.resetAllEditTextEvent.observe(viewLifecycleOwner) {
            binding.edit01.setText("")
            binding.edit02.setText("")
            binding.edit03.setText("")
            binding.edit01.requestFocus()
            binding.scrollView.fullScroll(ScrollView.FOCUS_DOWN)
        }
        viewModel.showAnswerImageEvent.observe(viewLifecycleOwner) {
            binding.lottieAnswer.visibility = View.VISIBLE
            binding.lottieAnswer.playAnimation()
        }
        binding.edit01.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) binding.edit02.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.edit02.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.isNotEmpty() == true) binding.edit03.requestFocus()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        viewModel.resultText.observe(viewLifecycleOwner) {
            binding.resultText.text = it
        }
    }
}