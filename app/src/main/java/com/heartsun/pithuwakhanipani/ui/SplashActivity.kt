package com.heartsun.pithuwakhanipani.ui

import android.os.Bundle
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
import androidcommon.extension.toastS
import androidcommon.utils.UiState
import androidx.lifecycle.lifecycleScope
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.databinding.ActivitySplashBinding
import com.heartsun.pithuwakhanipani.utils.AppSignatureHelper

import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.net.URLEncoder

class SplashActivity : BaseActivity() {
    private val prefs by inject<Prefs>()


    private val binding by lazy {
        ActivitySplashBinding.inflate(layoutInflater)
    }
    lateinit var appID: String
    private val homeViewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        serverDetailsObserver()
        lifecycleScope.launchWhenCreated {
            delay(4000)
            navigateNext()
        }
    }

    private fun navigateNext() {
        binding.tvLoading.text = "connecting to server…"

        val appSignatureHelper: AppSignatureHelper = AppSignatureHelper(this)
        appID = appSignatureHelper.appSignatures[0].toString()

//       appID= URLEncoder.encode(appID, "utf-8").trim()
        if (prefs.isFirstTime) {
            homeViewModel.getServerDetailsFromAPI(appID)
//            binding.tvLoading.text = "connecting to server"
//            startActivity(OnBoardingActivity.newIntent(this))
        } else {
            startActivity(HomeActivity.newIntent(context = this))
        }
//        }

//        startActivity(HomeActivity.newIntent(context = this))

//        this@SplashActivity.finish()
    }

    private fun serverDetailsObserver() {
        homeViewModel.serverDetails.observe(this, {
            it ?: return@observe
            binding.apply {
                when (it) {
                    is UiState.Loading -> {
                        showProgress()
                    }
                    is UiState.Success -> {
                        hideProgress()
                        if (it.data?.responseCode.equals("0")) {
                            prefs.databaseUser = it.data?.dbUserName
                            prefs.databasePassword = it.data?.dbPassword
                            prefs.databaseName = it.data?.databaseName
                            prefs.serverIp = it.data?.dbServerName?.split(",")?.get(0)
                            prefs.serverPort = it.data?.dbServerName?.split(",")?.get(1)
                            startActivity(HomeActivity.newIntent(context = this@SplashActivity))
                        }
                    }
                    is UiState.Error -> {
                        hideProgress()
                        showErrorDialog(
                            isCancellable = false,
                            message = "सर्भरमा जडान गर्न सकेन!!! \n कृपया फेरि प्रयास गर्नुहोस्",
                            title = "सर्भरमा जडान गर्न सकेन!!!",
                            label = "फेरि प्रयास गर्नुहोस्",
                            onBtnClick = { retryClicked() })
                    }
                    else -> {
                        showErrorDialog(
                            isCancellable = false,
                            message = "सर्भरमा जडान गर्न सकेन!!! \n कृपया फेरि प्रयास गर्नुहोस्",
                            title = "सर्भरमा जडान गर्न सकेन!!!",
                            label = "फेरि प्रयास गर्नुहोस्",
                            onBtnClick = { retryClicked() })

                    }
                }
            }
        })
    }

    private fun retryClicked() {
        homeViewModel.getServerDetailsFromAPI(appID)
    }


}