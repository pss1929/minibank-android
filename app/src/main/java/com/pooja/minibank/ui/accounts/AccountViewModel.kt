package com.pooja.minibank.ui.accounts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pooja.minibank.domain.model.account.Account
import com.pooja.minibank.domain.usecase.account.GetAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val getAccountUseCase: GetAccountUseCase) : ViewModel(){

    private val _account = MutableStateFlow<List<Account>> (emptyList())
    val accounts = _account.asStateFlow()

    fun getAccounts(){
        viewModelScope.launch {
            val data = getAccountUseCase()
            _account.value = data
        }
    }

}