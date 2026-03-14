package com.pooja.minibank.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pooja.minibank.core.utils.ResponseState
import com.pooja.minibank.domain.model.response.VerifyOtpResponse
import com.pooja.minibank.domain.usecase.auth.VerifyOtpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class OtpViewModel @Inject constructor(private val verifyOtpUseCase : VerifyOtpUseCase) : ViewModel() {

    //Verify OTP functionality
    private val _verifyOtpResponse = MutableLiveData<ResponseState<VerifyOtpResponse>>()
    val verifyOtpResponse : LiveData<ResponseState<VerifyOtpResponse>> = _verifyOtpResponse
    fun verifyOtp(sessionId : String, otp : String)
    {
        viewModelScope.launch {
            _verifyOtpResponse.value = ResponseState.Loading()

            val result = verifyOtpUseCase(sessionId, otp)
            _verifyOtpResponse.value = result
        }
    }
}