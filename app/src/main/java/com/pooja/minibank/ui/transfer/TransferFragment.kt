package com.pooja.minibank.ui.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.pooja.minibank.R
import com.pooja.minibank.core.utils.SnackBarUtil
import com.pooja.minibank.core.utils.visible
import com.pooja.minibank.data.local.pref.TokenManager
import com.pooja.minibank.databinding.FragmentTransferBinding
import com.pooja.minibank.domain.model.transfer.TransferRequest
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class TransferFragment : Fragment(R.layout.fragment_transfer) {

    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private val viewModel: TransferViewModel by viewModels()

    private var selectedAccountId: String? = null
    private var selectedBeneficiaryId: String? = null

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentTransferBinding.bind(view)
        binding.toolbar.setTitle("Transfer")

        observeData()

        viewModel.loadAccounts()
        viewModel.loadBeneficiaries()

        setupDropdownClicks()

        clearErrorForTextInputLayout(binding.tilAmount, binding.etAmount)

        binding.btnTransfer.setOnClickListener {
            performTransfer()
        }
    }

    private fun setupDropdownClicks() {

        binding.actFromAccount.setOnClickListener {
            binding.actFromAccount.showDropDown()
        }

        binding.actBeneficiary.setOnClickListener {
            binding.actBeneficiary.showDropDown()
        }
    }

    private fun observeData() {

        // ACCOUNT DROPDOWN

        viewModel.accounts.observe(viewLifecycleOwner) { accounts ->
            val accountList = accounts.map {
                "${it.name} (${it.maskedNumber})"
            }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                accountList
            )

            binding.actFromAccount.setAdapter(adapter)

            binding.actFromAccount.setOnItemClickListener { _, _, position, _ ->
                selectedAccountId = accounts[position].id
            }
        }


        // BENEFICIARY DROPDOWN

        viewModel.beneficiaries.observe(viewLifecycleOwner) { beneficiaries ->

            val names = beneficiaries.map {
                "${it.name} (${it.accountMasked})"
            }

            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                names
            )

            binding.actBeneficiary.setAdapter(adapter)

            binding.actBeneficiary.setOnItemClickListener { _, _, position, _ ->
                selectedBeneficiaryId = beneficiaries[position].id
            }
        }


        // TRANSFER RESULT

        viewModel.transferResult.observe(viewLifecycleOwner) {

            binding.progressTransfer.visibility = View.GONE

            when (it.status) {

                "SUCCESS" -> {

                    Toast.makeText(
                        requireContext(),
                        "Transfer Successful\nRef: ${it.referenceNumber}",
                        Toast.LENGTH_LONG
                    ).show()
                    clearForm()
                    findNavController().navigate(R.id.accountFragment)
                }

                "FAILED" -> {

                    Toast.makeText(
                        requireContext(),
                        it.message ?: "Transfer Failed",
                        Toast.LENGTH_LONG
                    ).show()
                }

                "DUPLICATE" -> {

                    Toast.makeText(
                        requireContext(),
                        "Duplicate transfer request",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        clearForm()
    }

    private fun performTransfer() {

        val amountText = binding.etAmount.text.toString()

        if (selectedAccountId == null) {

            SnackBarUtil.showError(binding.root,"Please select account" )
            return
        }

        if (selectedBeneficiaryId == null) {
            SnackBarUtil.showError(binding.root,"Please select beneficiary")
            return
        }

        if (amountText.isEmpty()) {

            binding.tilAmount.error=  "Amount is required"
            return
        }

        val request = TransferRequest(
            fromAccountId = selectedAccountId!!,
            beneficiaryId = selectedBeneficiaryId!!,
            amount = amountText.toDouble(),
            currency = "INR",
            note = binding.etNote.text.toString()
        )

        binding.progressTransfer.visible()

        viewModel.transfer(
            token = tokenManager.getAccessToken().toString(),
            request = request
        )
    }

    private fun clearErrorForTextInputLayout(tilValue: TextInputLayout, tieValue: TextInputEditText)
    {
        tieValue.addTextChangedListener {
            tilValue.error = null
            tilValue.isErrorEnabled = false
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearForm() {

        binding.etAmount.text?.clear()
        binding.etNote.text?.clear()

        binding.actFromAccount.setText("")
        binding.actBeneficiary.setText("")

        selectedAccountId = null
        selectedBeneficiaryId = null
    }
}