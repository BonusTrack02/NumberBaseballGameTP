package com.bonustrack02.numberbaseballgametp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bonustrack02.numberbaseballgametp.databinding.ActivityBeginningBinding

class BeginningActivity : AppCompatActivity() {
    private val binding: ActivityBeginningBinding by lazy { ActivityBeginningBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        handler.sendEmptyMessageDelayed(0, 2000)
    }

    private val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val intent = Intent(this@BeginningActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}