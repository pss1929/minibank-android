package com.pooja.minibank.ui.accounts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.pooja.minibank.R
import com.pooja.minibank.core.utils.Constants
import com.pooja.minibank.core.utils.UiState
import com.pooja.minibank.core.utils.gone
import com.pooja.minibank.core.utils.visible
import com.pooja.minibank.data.local.pref.PreferenceManager
import com.pooja.minibank.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AccountFragment : Fragment() {


    @Inject lateinit var pref : PreferenceManager
    private var _binding : FragmentAccountBinding?=null
    private val binding get() = _binding!!

    private val viewModel: AccountViewModel by viewModels()

    private val adapter = AccountsAdapter {

        val action = AccountFragmentDirections.actionAccountFragmentToTransactionFragment()
        findNavController().navigate(action)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View
    {
        _binding = FragmentAccountBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUI()

        binding.swipeLayout.setOnRefreshListener {
            callAccountApi()
        }
        //call API account
        callAccountApi()

        observeAccounts()

    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    private fun callAccountApi() {
        viewModel.getAccounts()
    }

    private fun initializeUI() {
        val username = pref.getStringPref(Constants.SP_USERNAME)

        val name = username?.let { Constants.getName(it) }
        val initials = name?.let { Constants.getInitialLetter(it) }

        binding.tvName.text = name
        binding.tvProfile.text = initials

        binding.rvAccounts.adapter = adapter

        binding.swipeLayout.setColorSchemeResources(R.color.primary)

        binding.btnRetry.setOnClickListener {
            callAccountApi()
        }
    }

    private fun observeAccounts() {

        lifecycleScope.launchWhenStarted {

            viewModel.accounts.collect {
                when(it)
                {
                    is UiState.Loading ->{
                        binding.swipeLayout.isRefreshing = true
                    }

                    is UiState.Success ->{
                        adapter.submitList(it.data)
                        binding.rvAccounts.visible()
                        binding.tvNoData.gone()
                        binding.llError.gone()
                        binding.swipeLayout.isRefreshing = false
                    }

                    is UiState.Error ->{
                        binding.llError.visible()
                        binding.rvAccounts.gone()
                        binding.llError.gone()

                        binding.swipeLayout.isRefreshing = false
                    }

                    is UiState.Empty ->{
                        binding.rvAccounts.visible()
                        binding.tvNoData.gone()
                        binding.llError.gone()
                        binding.swipeLayout.isRefreshing = false
                    }
                }
                binding.swipeLayout.isRefreshing =false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}