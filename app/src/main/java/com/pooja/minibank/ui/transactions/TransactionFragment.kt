package com.pooja.minibank.ui.transactions


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pooja.minibank.R
import com.pooja.minibank.databinding.FragmentTransactionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class TransactionFragment : Fragment() {

    private  var _binding: FragmentTransactionBinding? =null
    private val binding get() = _binding!!


    private val viewModel: TransactionViewModel by viewModels()


    private lateinit var adapter: TransactionsAdapter

    private var accountId: String = ""

    private var fromDate = ""
    private var toDate = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View
    {
        _binding = FragmentTransactionBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TransactionsAdapter(requireContext())

        accountId = arguments?.getString("accountId") ?: ""

        backClickHandle()

        setupRecyclerView()

        setupFilters()

        observeTransactions()

        loadLast7Days()
    }

    private fun backClickHandle() {

        binding.toolbar.setTitle("Transaction Detail")
        binding.toolbar.setNavigationIcon(R.drawable.ic_back)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getTransaction(accountId, fromDate, toDate)
        }
    }

    private fun setupFilters() {

        binding.btn7Days.setOnClickListener {
            loadLast7Days()
        }

        binding.btn30Days.setOnClickListener {

            val range = getDateRange(30)

            fromDate = range.first
            toDate = range.second

            viewModel.getTransaction(accountId, fromDate, toDate)
        }
    }

    private fun observeTransactions() {

        lifecycleScope.launch {

            viewModel.transactionData.collect {

                adapter.submitList(it)

                binding.swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun loadLast7Days() {

        val range = getDateRange(7)

        fromDate = range.first
        toDate = range.second

        viewModel.getTransaction(accountId, fromDate, toDate)
    }

    private fun getDateRange(days: Int): Pair<String, String> {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val today = LocalDate.now()

        val from = today.minusDays(days.toLong())

        return Pair(
            from.format(formatter),
            today.format(formatter)
        )
    }

}