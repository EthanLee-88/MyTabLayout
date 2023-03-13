package com.ethan.launcherkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.ethan.launcherkt.launch.EasyConfig
import com.ethan.launcherkt.launch.EasyConstance

class LauncherKtMainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "LauncherKtMainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher_kt_main)
        val text: TextView = findViewById(R.id.id_test_kt_text)
        text.setOnClickListener {
            test()
            setResult(888)
            finish()
        }
    }

    private fun test() {
        val config: EasyConfig = intent.extras?.get(EasyConstance.EASY_CONFIG_STR) as EasyConfig
        Log.d(TAG, "config = ${config.configStr + config.code}")
    }
}