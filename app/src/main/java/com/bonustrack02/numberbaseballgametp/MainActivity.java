package com.bonustrack02.numberbaseballgametp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bonustrack02.numberbaseballgametp.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kakao.adfit.ads.ba.BannerAdView;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int n1, n2, n3, countStrike, countBall;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.kakaoAdview.setClientId("DAN-zpFTwikubnRm0cE7");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                AdRequest adRequest = new AdRequest.Builder().build();
                binding.adview.loadAd(adRequest);
            }
        });

        Random rnd = new Random();
        n1 = rnd.nextInt(9) + 1;
        do {
            n2 = rnd.nextInt(9) + 1;
        } while (n1 == n2);

        do {
            n3 = rnd.nextInt(9) + 1;
        } while (n1 == n3 || n2 == n3);

        binding.edit01.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) binding.edit02.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.edit02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) binding.edit03.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.btnAnswer.setOnClickListener(view -> {
            int x1, x2, x3;


            try {
                String s1 = binding.edit01.getText().toString();
                x1 = Integer.parseInt(s1);
                String s2 = binding.edit02.getText().toString();
                x2 = Integer.parseInt(s2);
                String s3 = binding.edit03.getText().toString();
                x3 = Integer.parseInt(s3);
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, "숫자를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }


            if (x1 == n1)
                countStrike++;
            else if (x1 == n2)
                countBall++;
            else if (x1 == n3)
                countBall++;

            if (x2 == n2)
                countStrike++;
            else if (x2 == n1)
                countBall++;
            else if (x2 == n3)
                countBall++;

            if (x3 == n3)
                countStrike++;
            else if (x3 == n2)
                countBall++;
            else if (x3 == n1)
                countBall++;

            binding.resultText.append(x1 + " " + x2 + " " + x3 + "   " + countStrike + "Strike " + countBall + "Ball\n");

            if (countStrike == 3) {
                binding.endText.setText(x1 + " " + x2 + " " + x3 + " 정답입니다!");
                binding.endText.setVisibility(View.VISIBLE);
                binding.endImage.setVisibility(View.VISIBLE);
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(binding.edit03.getWindowToken(), 0);
            }

            countBall = 0;
            countStrike = 0;

            binding.edit01.setText("");
            binding.edit02.setText("");
            binding.edit03.setText("");

            binding.edit01.requestFocus();

            binding.srcollview.fullScroll(ScrollView.FOCUS_DOWN);
            binding.kakaoAdview.loadAd();
        });
    }

    @Override
    protected void onResume () {
        super.onResume();

        if (binding.kakaoAdview != null) binding.kakaoAdview.resume();
    }

    @Override
    protected void onPause () {
        super.onPause();

        if (binding.kakaoAdview != null) binding.kakaoAdview.pause();
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();

        if (binding.kakaoAdview != null) binding.kakaoAdview.destroy();
    }

    boolean wasPressed = false;
    long lastTime;

    @Override
    public void onBackPressed () {
        if (!wasPressed) {
            Toast.makeText(this, "한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
            wasPressed = true;
            lastTime = new Date().getTime();
        } else {
            long nowTime = new Date().getTime();
            if (nowTime - lastTime > 3000) wasPressed = false;
            else super.onBackPressed();
        }
    }
}