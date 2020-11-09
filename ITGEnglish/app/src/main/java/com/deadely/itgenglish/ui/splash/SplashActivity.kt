package com.deadely.itgenglish.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.deadely.itgenglish.R
import com.deadely.itgenglish.ui.login.LoginActivity
import com.deadely.itgenglish.ui.main.MainActivity
import com.deadely.itgenglish.utils.START_LOGIN_SCREEN

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler().postDelayed(
            {
                startActivityForResult(
                    Intent(this@SplashActivity, LoginActivity::class.java),
                    START_LOGIN_SCREEN
                )
            },
            2 * 1000.toLong()
        )
    }

    private fun openMainScreen() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            START_LOGIN_SCREEN -> {
                when (resultCode) {
                    RESULT_OK -> openMainScreen()
                }
            }
        }
    }
}
