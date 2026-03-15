package com.pooja.minibank.ui.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pooja.minibank.databinding.ItemAccountBinding
import com.pooja.minibank.domain.model.account.Account

class AccountsAdapter(
    val onClick: (Account) -> Unit) : ListAdapter<Account, AccountsAdapter.AccountViewHolder>(DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {

        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AccountViewHolder(binding)
    }

    class AccountViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {

        val item = getItem(position)

        holder.binding.tvAccountType.text = item.name
        holder.binding.tvAccountNumber.text = item.maskedNumber
        holder.binding.tvAccountBalance.text = "₹ ${item.balance}"

        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }

    companion object {

        val DIFF = object : DiffUtil.ItemCallback<Account>() {

            override fun areItemsTheSame(oldData: Account, newData: Account) =
                oldData.id == newData.id

            override fun areContentsTheSame(oldData: Account, newData: Account) =
                oldData == newData
        }
    }
}