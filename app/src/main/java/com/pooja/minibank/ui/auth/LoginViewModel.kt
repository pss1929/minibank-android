package com.pooja.minibank.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pooja.minibank.core.utils.ResponseState
import com.pooja.minibank.domain.model.response.LoginResponse
import com.pooja.minibank.domain.usecase.auth.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    //Login functionality
    private val _loginResponse = MutableLiveData<ResponseState<LoginResponse>>()
    val loginResponse : LiveData<ResponseState<LoginResponse>> = _loginResponse
    fun login(username : String, password : String)
    {
        viewModelScope.launch {
            _loginResponse.value = ResponseState.Loading()

            val result = loginUseCase(username, password)
            _loginResponse.value = result
        }
    }
}