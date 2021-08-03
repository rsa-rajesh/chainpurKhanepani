package com.heartsun.pithuwakhanipani.customView

import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidcommon.extension.loggerE
import androidx.core.content.ContextCompat.startActivity
import androidx.viewpager2.widget.ViewPager2
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.adapters.OnBoardingPagerAdapter
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.databinding.OnboardingViewBinding
import com.heartsun.pithuwakhanipani.entity.OnBoardingPage
import com.heartsun.pithuwakhanipani.ui.HomeActivity
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateActivity
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator

class OnBoardingView @JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val numberOfPages by lazy { OnBoardingPage.values().size }
    private val prefManager: Prefs
    private lateinit var viewPager2: ViewPager2
    var positions = 0;
    private val binding: OnboardingViewBinding =
        OnboardingViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setUpSlider(binding.root)
        Log.d("onboard" ,"aayoi")
        addingButtonsClickListeners()
        prefManager = Prefs(context.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE))
    }

    private fun setUpSlider(view: View) {
        with(binding.slider1) {
            adapter = OnBoardingPagerAdapter()
            setPageTransformer { page, position ->
                setParallaxTransformation(page, position)
            }
            viewPager2 = binding.slider1

            addSlideChangeListener()
            Log.d("onboard" ,"aayoi 2")

            val wormDotsIndicator = view.findViewById<WormDotsIndicator>(R.id.page_indicator)
            wormDotsIndicator.setViewPager2(this)
        }
    }


    private fun addSlideChangeListener() {
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (numberOfPages > 1) {
                    val newProgress = (position + positionOffset) / (numberOfPages - 1)
                    binding.onboardingRoot.progress = newProgress
                }
                when (position) {
                    1 -> {
                        binding.titleTv.text = context.getString(R.string.onboard_second_title)
                        binding.descTV.text = context.getString(R.string.onboard_second_details)
                    }
                    2 -> {
                        binding.titleTv.text = context.getString(R.string.onboard_third_title)
                        binding.descTV.text = context.getString(R.string.onboard_third_details)
                    }
                    else -> {
                        binding.titleTv.text = context.getString(R.string.onboard_first_title)
                        binding.descTV.text = context.getString(R.string.onboard_first_details)
                    }
                }


                positions = position;

            }
        })
    }

    private fun addingButtonsClickListeners() {
        binding.startBtn.setOnClickListener {
            if (positions == 2) {
                setFirstTimeLaunchToFalse()
                navigateToMainActivity()
            } else {
                navigateToNextSlide()
            }
        }

    }

    private fun setFirstTimeLaunchToFalse() {
    }

    private fun navigateToNextSlide() {
        val nextSlidePos: Int = binding.slider1.currentItem.plus(1) ?: 0
        binding.slider1.setCurrentItem(nextSlidePos, true)
    }

    private fun navigateToMainActivity() {

        val intent = Intent(context, HomeActivity::class.java).apply {
            flags= Intent.FLAG_ACTIVITY_CLEAR_TASK
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }
}

fun setParallaxTransformation(page: View, position: Float) {
    page.apply {
        val parallaxView = page.findViewById<ImageView>(R.id.img)
        when {
            position < -1 -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                alpha = 1f
            position <= 1 -> { // [-1,1]
                parallaxView.translationX = -position * (width / 2) //Half the normal speed
            }
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                alpha = 1f
        }
    }

}