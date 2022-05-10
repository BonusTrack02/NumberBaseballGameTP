package com.bonustrack02.numberbaseballgametp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bonustrack02.numberbaseballgametp.databinding.ActivityMainBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kakao.adfit.ads.AdListener;
import com.kakao.adfit.ads.ba.BannerAdView;

import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int n1, n2, n3, countStrike, countBall, cnt;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.kakaoAdview.setClientId("DAN-zpFTwikubnRm0cE7");
        binding.kakaoAdview.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Log.i("AdState", "loaded");
            }

            @Override
            public void onAdFailed(int i) {
                Log.i("AdState", i + "");
            }

            @Override
            public void onAdClicked() {
                Log.i("AdState", "clicked");
            }
        });
        binding.kakaoAdview.loadAd();

        binding.fab.setOnClickListener(view -> {
            new MaterialAlertDialogBuilder(this)
                    .setTitle("도움말")
                    .setMessage("1부터 9사이의 중복되지 않는 랜덤한 숫자가 3개 생성됩니다.\n" +
                            "해당하는 숫자가 같은 위치에 있으면 스트라이크, 다른 위치에 있으면 볼입니다.\n")
                    .show();
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

            cnt++;

            binding.resultText.append("시도 " + cnt + " : " + x1 + " " + x2 + " " + x3 + "   " + countStrike + "Strike " + countBall + "Ball\n");

            if (countStrike == 3) {
                binding.endText.setText(x1 + " " + x2 + " " + x3 + " 정답입니다!");
                binding.endText.setVisibility(View.VISIBLE);
                binding.endImage.setVisibility(View.VISIBLE);
                cnt = 0;
                InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(binding.edit03.getWindowToken(), 0);
                binding.btnAnswer.setText("재시작");
                binding.btnAnswer.setOnClickListener(view1 -> {
                    binding.endText.setText("");
                    binding.endText.setVisibility(View.GONE);
                    binding.endImage.setVisibility(View.GONE);
                    binding.resultText.setText("");
                    binding.btnAnswer.setText("정답 확인");
                    return;
                });
            }

            countBall = 0;
            countStrike = 0;

            binding.edit01.setText("");
            binding.edit02.setText("");
            binding.edit03.setText("");

            binding.edit01.requestFocus();

            binding.srcollview.fullScroll(ScrollView.FOCUS_DOWN);
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