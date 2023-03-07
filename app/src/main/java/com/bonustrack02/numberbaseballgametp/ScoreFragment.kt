package com.bonustrack02.numberbaseballgametp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bonustrack02.numberbaseballgametp.databinding.FragmentScoreBinding

class ScoreFragment: Fragment() {
    private val binding: FragmentScoreBinding by lazy { FragmentScoreBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}