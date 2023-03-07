package com.bonustrack02.numberbaseballgametp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ScrollView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bonustrack02.numberbaseballgametp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kakao.adfit.ads.AdListener
import java.util.Date
import java.util.Random

class MainActivity : AppCompatActivity() {
    var n1 = 0
    var n2 = 0
    var n3 = 0
    var countStrike = 0
    var countBall = 0
    var cnt = 0
    val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.kakaoAdview.setClientId(BuildConfig.SMALLBANNERCLIENTID)
        binding.kakaoAdview.setAdListener(object : AdListener {
            override fun onAdLoaded() {
                Log.i("AdState", "loaded")
            }

            override fun onAdFailed(i: Int) {
                Log.i("AdState", i.toString() + "")
            }

            override fun onAdClicked() {
                Log.i("AdState", "clicked")
            }
        })
        binding.kakaoAdview.loadAd()
        binding.fab.setOnClickListener { view: View? ->
            MaterialAlertDialogBuilder(this)
                    .setTitle("도움말")
                    .setMessage("""
    1부터 9사이의 중복되지 않는 랜덤한 숫자가 3개 생성됩니다.
    해당하는 숫자가 같은 위치에 있으면 스트라이크, 다른 위치에 있으면 볼입니다.
    """.trimIndent())
                .show()
        }
        val rnd = Random()
        n1 = rnd.nextInt(9) + 1
        do {
            n2 = rnd.nextInt(9) + 1
        } while (n1 == n2)
        do {
            n3 = rnd.nextInt(9) + 1
        } while (n1 == n3 || n2 == n3)
        binding.edit01.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) binding.edit02.requestFocus()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding.edit02.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                if (charSequence.isNotEmpty()) binding.edit03.requestFocus()
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        binding.btnAnswer.setOnClickListener { view: View? ->
            val x1: Int
            val x2: Int
            val x3: Int
            try {
                val s1 = binding.edit01.text.toString()
                x1 = s1.toInt()
                val s2 = binding.edit02.text.toString()
                x2 = s2.toInt()
                val s3 = binding.edit03.text.toString()
                x3 = s3.toInt()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "숫자를 제대로 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (x1 == n1) countStrike++ else if (x1 == n2) countBall++ else if (x1 == n3) countBall++
            if (x2 == n2) countStrike++ else if (x2 == n1) countBall++ else if (x2 == n3) countBall++
            if (x3 == n3) countStrike++ else if (x3 == n2) countBall++ else if (x3 == n1) countBall++
            cnt++
            binding.resultText.append("시도 $cnt : $x1 $x2 $x3   ${countStrike}Strike ${countBall}Ball\n")
            if (countStrike == 3) {
                binding.endText.text = "$x1 $x2 $x3 정답입니다!"
                binding.endText.visibility = View.VISIBLE
                binding.endImage.visibility = View.VISIBLE
                cnt = 0
                val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(binding.edit03.windowToken, 0)
            }
            countBall = 0
            countStrike = 0
            binding.edit01.setText("")
            binding.edit02.setText("")
            binding.edit03.setText("")
            binding.edit01.requestFocus()
            binding.srcollview.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.kakaoAdview.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.kakaoAdview.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.kakaoAdview.destroy()
    }

    var wasPressed = false
    var lastTime: Long = 0
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!wasPressed) {
            Toast.makeText(this, "한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()
            wasPressed = true
            lastTime = Date().time
        } else {
            val nowTime = Date().time
            if (nowTime - lastTime > 3000) wasPressed = false else super.onBackPressed()
        }
    }
}