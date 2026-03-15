package com.pooja.minibank.ui.transfer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pooja.minibank.data.local.dao.AccountDao
import com.pooja.minibank.data.local.enity.AccountEntity
import com.pooja.minibank.domain.model.transfer.Beneficiary
import com.pooja.minibank.domain.model.transfer.TransferRequest
import com.pooja.minibank.domain.model.transfer.TransferResult
import com.pooja.minibank.domain.usecase.transfer.GetBeneficiariesUseCase
import com.pooja.minibank.domain.usecase.transfer.TransferMoneyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TransferViewModel @Inject constructor(
    private val accountDao: AccountDao,
    private val getBeneficiariesUseCase: GetBeneficiariesUseCase,
    private val transferMoneyUseCase: TransferMoneyUseCase
) : ViewModel() {

    val accounts = MutableLiveData<List<AccountEntity>>()

    val beneficiaries = MutableLiveData<List<Beneficiary>>()

    val transferResult = MutableLiveData<TransferResult>()

    fun loadAccounts() {

        viewModelScope.launch {

            accounts.value = accountDao.getAccountList()
        }
    }

    fun loadBeneficiaries() {

        viewModelScope.launch {

            beneficiaries.value = getBeneficiariesUseCase()
        }
    }

    fun transfer(token: String, request: TransferRequest) {

        viewModelScope.launch {

            transferResult.value =
                transferMoneyUseCase(token, request)
        }
    }
}