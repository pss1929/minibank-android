package com.pooja.minibank.ui.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pooja.minibank.R
import com.pooja.minibank.core.utils.Constants
import com.pooja.minibank.core.utils.NetworkUtil
import com.pooja.minibank.core.utils.ResponseState
import com.pooja.minibank.core.utils.SnackBarUtil
import com.pooja.minibank.core.utils.enableSecureScreen
import com.pooja.minibank.core.utils.gone
import com.pooja.minibank.core.utils.visible
import com.pooja.minibank.data.local.pref.PreferenceManager
import com.pooja.minibank.data.local.pref.TokenManager
import com.pooja.minibank.databinding.ActivityOtpBinding
import com.pooja.minibank.ui.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OtpActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOtpBinding
    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var prefManager: PreferenceManager

    private val viewmodel : OtpViewModel by viewModels()
    private var sessionId: String? =""
    private var mobileNumber : String? = ""

    private var username : String ?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableSecureScreen()
        enableEdgeToEdge()

        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()

        setUpOTPScreen()

        clickListeners()

        observeVerifyOtp()
    }

    private fun observeVerifyOtp() {
        viewmodel.verifyOtpResponse.observe(this){ state->
            when(state)
            {
                is ResponseState.Loading ->{
                    binding.progressbar.visible()
                }
                is ResponseState.Success ->{
                    binding.progressbar.gone()

                    val response = state.data

                    with(response)
                    {
                        accessToken?.let { token -> tokenManager.saveAccessToken(token) }
                        refreshToken?.let { refresh -> tokenManager.saveRefreshToken(refresh) }
                        expiresIn?.let {expiresIn ->
                            val expiryTime = System.currentTimeMillis() + expiresIn * 1000
                            tokenManager.saveExpiryIn(expiryTime)
                        }

                        Log.d("Authorization",tokenManager.getExpiryIn().toString())

                    }

                    prefManager.addPref(Constants.SP_IS_LOGGED_IN, true)
                    prefManager.addPref(Constants.SP_USERNAME, username?:"User")

                    navigateToNextScreen()

                }
                is ResponseState.Error ->{
                    binding.progressbar.gone()
                    SnackBarUtil.showError(binding.root, "Error : ${state.message}")
                }
            }
        }
    }

    private fun navigateToNextScreen() {
        val intent = Intent(this@OtpActivity, DashboardActivity::class.java).apply {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }

    private fun clickListeners() {
        binding.btnVerifyOtp.setOnClickListener {
            val enterOtp = getEnterOtp()
            if (enterOtp.length != 6) {
                SnackBarUtil.showError(binding.root, getString(R.string.enter_valid_otp))
                return@setOnClickListener
            }

            performOtpVerifyApiCall(enterOtp)
        }
    }

    private fun performOtpVerifyApiCall(enterOtp: String) {
        if (NetworkUtil.isNetworkAvailable(this@OtpActivity)) {
            sessionId?.let {
                viewmodel.verifyOtp(sessionId = it, enterOtp)
            }
        } else {
            SnackBarUtil.showError(
                binding.root,
                getString(R.string.no_internet_connection_available)
            )

        }
    }

    private fun initialization() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        intent?.let {
            sessionId = it.getStringExtra("sessionId")
            mobileNumber = it.getStringExtra("maskedPhone")
            username =it.getStringExtra("username")
        }

        mobileNumber?.let {
            binding.tvMobileNumber.text =
                getString(R.string.a_6_digit_code_has_been_sent_to, mobileNumber)
        }
    }

    private fun setUpOTPScreen()
    {
        with(binding)
        {
            moveNext(et1, et2)
            moveNext(et2, et3)
            moveNext(et3, et4)
            moveNext(et4,et5)
            moveNext(et5,et6)
        }
    }

    private fun getEnterOtp() :String{
        return binding.et1.text.toString()+binding.et2.text.toString()+binding.et3.text.toString()+binding.et4.text.toString()+binding.et5.text.toString()+binding.et6.text.toString()
    }


    private fun moveNext(currentEditText : EditText, nextEditText: EditText)
    {
        currentEditText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                if(s?.length == 1)
                {
                    nextEditText.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}