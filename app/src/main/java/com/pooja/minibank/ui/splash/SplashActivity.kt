package com.pooja.minibank.ui.splash

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pooja.minibank.R
import com.pooja.minibank.core.security.SecurityUtils
import com.pooja.minibank.core.utils.Constants
import com.pooja.minibank.data.local.pref.PreferenceManager
import com.pooja.minibank.databinding.ActivitySplashBinding
import com.pooja.minibank.ui.auth.LoginActivity
import com.pooja.minibank.ui.dashboard.DashboardActivity
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

        checkRootDetection()
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

    private fun checkRootDetection() {
        if (SecurityUtils.isDeviceRooted()) {
            AlertDialog.Builder(this)
                .setTitle("Security Alert")
                .setMessage("This app cannot run on rooted devices.")
                .setCancelable(false)
                .setPositiveButton("Exit") { _, _ -> finish() }
                .show()
        }
    }

    private fun navigateToNextScreen() {
        Handler(Looper.getMainLooper()).postDelayed({

            if(pref.getBooleanPref(Constants.SP_ONBOARDING_DONE))
            {
                if(pref.getBooleanPref(Constants.SP_IS_LOGGED_IN)) {

                   if(Constants.isBiometricAvailable(this@SplashActivity) && pref.getBooleanPref("biometric_enabled"))
                    showBiometricPrompt()
                   else {
                       startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                       finish()
                   }
                }
                else {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }
            }
            else
            {
                startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                finish()
            }
        }, 3000)
    }

    private fun showBiometricPrompt() {

        val executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)


                    startActivity(Intent(this@SplashActivity, DashboardActivity::class.java))
                    finish()

                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Confirm your fingerprint")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
