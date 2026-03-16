package com.pooja.minibank.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.pooja.minibank.R
import com.pooja.minibank.core.utils.Constants
import com.pooja.minibank.core.utils.SnackBarUtil
import com.pooja.minibank.data.local.pref.PreferenceManager
import com.pooja.minibank.databinding.ActivityDashboardBinding
import com.pooja.minibank.ui.BaseActivity
import com.pooja.minibank.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : BaseActivity() {

    private lateinit var binding : ActivityDashboardBinding

    @Inject lateinit var  pref : PreferenceManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        binding = ActivityDashboardBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handleNavigationComponent()
    }


    private fun handleNavigationComponent() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navHostController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navHostController)

        navHostController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.transactionFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }

                else -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(Constants.isBiometricAvailable(this@DashboardActivity) && pref.getBooleanPref("biometric_enabled"))
            showBiometricPrompt()
        else {
            finish()
        }


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



                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    SnackBarUtil.showError(binding.root, "Login Failed")
                    finish()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    SnackBarUtil.showError(binding.root, "Login Failed")
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