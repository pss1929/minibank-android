package com.pooja.minibank.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pooja.minibank.domain.model.transaction.Transaction
import com.pooja.minibank.domain.usecase.transaction.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase
)  : ViewModel(){
    private val  _transactionData = MutableStateFlow<List<Transaction>>(emptyList())
    val transactionData = _transactionData.asStateFlow()

    private var page = 1

    fun getTransaction(accountId : String, from :String, to :String){
        viewModelScope.launch {
            val response = getTransactionUseCase(
                accountId,from,to,page
            )

            _transactionData.value = response.first

            response.second?.let { page = it }
        }
    }

    fun getFilterDateRange(days : Int) : Pair<String, String>{
        val formmater = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now()
        val from = today.minusDays(days.toLong())

        return Pair(
            from.format(formmater),
            today.format(formmater)
        )
    }


}