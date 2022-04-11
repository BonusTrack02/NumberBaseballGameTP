package com.bonustrack02.numberbaseballgametp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bonustrack02.numberbaseballgametp.databinding.ActivityBeginningBinding;

public class BeginningActivity extends AppCompatActivity {

    ActivityBeginningBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBeginningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}