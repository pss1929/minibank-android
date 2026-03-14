package com.pooja.minibank.ui.onboarding

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.pooja.minibank.databinding.ActivityOnBoardingBinding
import com.pooja.minibank.domain.model.OnBoardingItem
import com.pooja.minibank.ui.auth.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private val onBoardingViewModel : OnBoardingViewModel by viewModels()
    private lateinit var binding : ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //observe onboarding item data
        observeOnboardingData()
    }

    private fun observeOnboardingData() {
        onBoardingViewModel.onBoardingItem.observe(this) { list ->
            val adapter = OnBoardingAdapter(list)
            binding.onBoardingViewPager.adapter = adapter
            setupDots(0)

            binding.onBoardingViewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setupDots(position)
                    binding.btnNext.text = if (position == list.size - 1) "Get Started" else "Next"
                }
            })

            // button clicks
            buttonClickListeners(list)

        }
    }

    private fun buttonClickListeners(list: List<OnBoardingItem>) {

        //Handle next button click
        binding.btnNext.setOnClickListener {

            animateButton(it)
            if (binding.onBoardingViewPager.currentItem + 1 < list.size) {
                binding.onBoardingViewPager.currentItem += 1
            } else {
                navigateToNextScreen()
            }
        }

        binding.btnSkip.setOnClickListener {
            animateButton(it)
            navigateToNextScreen()
        }
    }

    //Navigate to next screen
    private fun navigateToNextScreen() {
        onBoardingViewModel.finishOnBoarding()
        startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
        finish()
    }

    //When click on button it animate
    private fun animateButton(view: View) {
        view.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).withEndAction {
            view.animate().scaleX(1f).scaleY(1f)
        }
    }

    private fun setupDots(position : Int)
    {
        val listSize = onBoardingViewModel.onBoardingItem.value?.size?:0
        val dots = arrayOfNulls<TextView>(listSize)
        binding.llDots.removeAllViews()

        for(i in dots.indices)
        {
            dots[i] = TextView(this)
            dots[i]?.text = "."
            dots[i]?.textSize = 55f
            dots[i]?.setTextColor(Color.GRAY)
            binding.llDots.addView(dots[i])
        }
        if(dots.isNotEmpty()) dots[position]?.setTextColor(Color.BLACK)
    }
}