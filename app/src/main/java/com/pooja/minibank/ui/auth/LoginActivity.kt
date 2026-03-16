package com.pooja.minibank.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pooja.minibank.R
import com.pooja.minibank.core.utils.NetworkUtil
import com.pooja.minibank.core.utils.ResponseState
import com.pooja.minibank.core.utils.SnackBarUtil
import com.pooja.minibank.core.utils.gone
import com.pooja.minibank.core.utils.visible
import com.pooja.minibank.data.local.pref.PreferenceManager
import com.pooja.minibank.databinding.ActivityLoginBinding
import com.pooja.minibank.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModels()

    @Inject lateinit var  pref : PreferenceManager

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initialization()

        onClickListeners()

        observeLogin()
    }


    private fun navigateToNextScreen() {
        val intent = Intent(this@LoginActivity, DashboardActivity::class.java).apply {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finishAffinity()
    }


    private fun initialization() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        clearErrorForTextInputLayout(binding.tilUsername, binding.tieUsername)
        clearErrorForTextInputLayout(binding.tilPassword, binding.tiePassword)

        val versionName = packageManager
            .getPackageInfo(packageName, 0).versionName

        binding.tvVersion.text =" Version : $versionName"

    }

    private fun observeLogin() {
        viewModel.loginResponse.observe(this){ state ->
            when(state)
            {
                is ResponseState.Loading ->{
                    showLoading(true)
                }
                is ResponseState.Success ->{
                    showLoading(false)
                    val response = state.data
                    Snackbar.make(binding.root, " ${response.otpRequired}", Snackbar.LENGTH_SHORT).show()

                    if(response.otpRequired == true)
                    {
                        val intent = Intent(this@LoginActivity, OtpActivity::class.java).apply {
                            putExtra("sessionId",response.sessionId)
                            putExtra("maskedPhone",response.maskedPhone)
                            putExtra("username", username)
                        }
                        startActivity(intent)
                        finishAffinity()
                    }
                }

                is ResponseState.Error ->{
                    showLoading(false)
                    SnackBarUtil.showError(binding.root,"Error : ${state.message}" )
                }
            }
        }
    }

    private fun onClickListeners() {

        binding.btnLogin.setOnClickListener {

             username = binding.tieUsername.text.toString().trim()
            val password = binding.tiePassword.text.toString().trim()

            if(validate(username, password)) return@setOnClickListener

            if(NetworkUtil.isNetworkAvailable(this@LoginActivity))
            {
                viewModel.login(username = username, password = password)
            }
            else{
                SnackBarUtil.showError(binding.root, getString(R.string.no_internet_connection_available))
            }
        }
    }

    private fun validate(username: String, password: String) : Boolean {
        if (username.isEmpty()) {
            binding.tilUsername.error = getString(R.string.username_is_required)
            return true
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = getString(R.string.password_is_required)
            return true
        }
        return false
    }

    private fun clearErrorForTextInputLayout(tilValue: TextInputLayout, tieValue: TextInputEditText)
    {
        tieValue.addTextChangedListener {
            tilValue.error = null
            tilValue.isErrorEnabled = false
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading)
        {
            binding.btnLogin.text = ""
            binding.progressbar.visible()
            binding.btnLogin.isEnabled = false

        }
        else{
            binding.btnLogin.text = getString(R.string.login)
            binding.progressbar.gone()
            binding.btnLogin.isEnabled = true
        }
    }
}

