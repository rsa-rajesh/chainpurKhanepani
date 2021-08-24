package com.heartsun.pithuwakhanipani.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidx.lifecycle.lifecycleScope
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.data.Prefs
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity() {
    private val prefs by inject<Prefs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launchWhenCreated {
            delay(4000)
            navigateNext()
        }
    }

    private fun navigateNext() {
//        if (prefs.isLoggedIn) {
//            startActivity(NewShipmentsActivity.newIntent(this))
//            // startActivity(HomeActivity.newIntent(this))
//        } else {
            if (prefs.isFirstTime) {
                startActivity(OnBoardingActivity.newIntent(this))
            } else {
            startActivity(HomeActivity.newIntent(context = this))
            }
//        }

//        startActivity(HomeActivity.newIntent(context = this))

        this@SplashActivity.finish()
    }
}