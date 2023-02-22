package com.bonustrack02.numberbaseballgametp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.bonustrack02.numberbaseballgametp.databinding.ActivityBeginningBinding;

public class BeginningActivity extends AppCompatActivity {

    ActivityBeginningBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBeginningBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handler.sendEmptyMessageDelayed(0, 2500);
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Intent intent = new Intent(BeginningActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}