package com.pooja.minibank.ui.transactions

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pooja.minibank.R
import com.pooja.minibank.databinding.ItemTransactionDetailBinding
import com.pooja.minibank.domain.model.transaction.Transaction

class TransactionsAdapter(private val context : Context) :
    ListAdapter<Transaction, TransactionsAdapter.TransactionViewHolder>(DIFF) {

    class TransactionViewHolder(val binding: ItemTransactionDetailBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder
    {
        val binding = ItemTransactionDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int
    ) {

        val item = getItem(position)

        holder.binding.tvCounterParty.text = item.counterparty
        holder.binding.tvNarration.text = item.narration
        holder.binding.tvStatus.text = item.status

        if (item.type == "DEBIT") {

            holder.binding.tvAmount.text = "- ₹${item.amount}"
            holder.binding.tvAmount.setTextColor(
                holder.itemView.context.getColor(
                    android.R.color.holo_red_dark
                )
            )

            holder.binding.cardType.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.red_light)
                )
            holder.binding.ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_debit))

        } else {

            holder.binding.tvAmount.text = "+ ₹${item.amount}"
            holder.binding.tvAmount.setTextColor(
                holder.itemView.context.getColor(
                    android.R.color.holo_green_dark
                )
            )
            holder.binding.cardType.backgroundTintList =
                ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context, R.color.green_light)
                )
            holder.binding.ivType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_credit))

        }
    }

    companion object {

        val DIFF = object : DiffUtil.ItemCallback<Transaction>() {

            override fun areItemsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Transaction,
                newItem: Transaction
            ): Boolean {

                return oldItem == newItem
            }
        }
    }
}