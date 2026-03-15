package com.pooja.minibank.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pooja.minibank.MainActivity
import com.pooja.minibank.core.utils.Constants
import com.pooja.minibank.data.local.pref.PreferenceManager
import com.pooja.minibank.databinding.ActivitySplashBinding
import com.pooja.minibank.ui.auth.LoginActivity
import com.pooja.minibank.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var  binding : ActivitySplashBinding

    @Inject lateinit var  pref : PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navigateToNextScreen()

    }

    private fun navigateToNextScreen() {
        Handler(Looper.getMainLooper()).postDelayed({

            if(pref.getBooleanPref(Constants.SP_ONBOARDING_DONE))
            {
                if(pref.getBooleanPref(Constants.SP_IS_LOGGED_IN)) {

                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                }
                else
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            }
            else
            {
                startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
            }

            finish()
        }, 3000)
    }
}
