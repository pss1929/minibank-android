package com.pooja.minibank.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pooja.minibank.databinding.ItemOnboardingBinding
import com.pooja.minibank.domain.model.OnBoardingItem

class OnBoardingAdapter(private val list : List<OnBoardingItem>
) : RecyclerView.Adapter<OnBoardingAdapter.OnboardingViewHolder>() {
    class OnboardingViewHolder(val binding : ItemOnboardingBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val binding = ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OnboardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvOnboardingTitle.text = item.title
        holder.binding.tvOnboardingDesc.text = item.desc

        Glide.with(holder.binding.ivOnboarding.context)
            .load(item.image)
            .into(holder.binding.ivOnboarding)

    }

    override fun getItemCount(): Int {
        return list.size
    }
}