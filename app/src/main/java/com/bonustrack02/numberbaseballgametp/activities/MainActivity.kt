package com.bonustrack02.numberbaseballgametp.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bonustrack02.numberbaseballgametp.BuildConfig
import com.bonustrack02.numberbaseballgametp.R
import com.bonustrack02.numberbaseballgametp.databinding.ActivityMainBinding
import com.bonustrack02.numberbaseballgametp.game.GameFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kakao.adfit.ads.AdListener
import java.util.Date

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
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
                .setPositiveButton(R.string.ok, null)
                .show()
        }
        val gameFragment = GameFragment()
        supportFragmentManager.beginTransaction().add(R.id.container_fragment, gameFragment).commit()
        binding.bnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_bnv_game -> supportFragmentManager.beginTransaction().show(gameFragment).commit()
            }
            return@setOnItemSelectedListener true
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

    private var wasPressed = false
    private var lastTime: Long = 0
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