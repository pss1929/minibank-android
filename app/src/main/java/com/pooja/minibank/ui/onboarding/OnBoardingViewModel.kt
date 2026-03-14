package com.pooja.minibank.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pooja.minibank.R
import com.pooja.minibank.core.utils.Constants
import com.pooja.minibank.data.local.pref.PreferenceManager
import com.pooja.minibank.domain.model.OnBoardingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val pref : PreferenceManager) : ViewModel(){

    private val _onBoardingItem = MutableLiveData<List<OnBoardingItem>>()
    val onBoardingItem : LiveData<List<OnBoardingItem>> = _onBoardingItem

    init {
        val list = ArrayList<OnBoardingItem>()

        list.add(OnBoardingItem("Welcome to MiniBank","Secure Mobile Banking App", R.drawable.onboarding_1))
        list.add(OnBoardingItem("Easy and Secure \n Banking","Access all accounts, transfer money and pay bills", R.drawable.onboarding_2))
        list.add(OnBoardingItem("Track Every Transaction","Stay updated with your spending and history", R.drawable.onboarding_3))
        _onBoardingItem.value = list
    }

    fun finishOnBoarding(){
        pref.addPref(Constants.SP_ONBOARDING_DONE, true)
    }
}