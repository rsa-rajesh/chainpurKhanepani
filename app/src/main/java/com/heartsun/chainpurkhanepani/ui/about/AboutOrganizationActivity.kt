package com.heartsun.chainpurkhanepani.ui.about

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.load
import androidcommon.extension.showErrorDialog
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import com.heartsun.chainpurkhanepani.databinding.ActivityAboutOrganizationBinding
import com.heartsun.chainpurkhanepani.ui.HomeViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutOrganizationActivity : BaseActivity() {

    private val binding by lazy {
        ActivityAboutOrganizationBinding.inflate(layoutInflater)
    }

    private val homeViewModel by viewModel<HomeViewModel>()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AboutOrganizationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    @DelicateCoroutinesApi
    private fun initView() {
        with(binding) {
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@AboutOrganizationActivity.finish()
            }
            toolbar.tvToolbarTitle.text = "संस्थाको बारेमा"
        }
        getAboutOrgFromDb()

    }

    @DelicateCoroutinesApi
    private fun getAboutOrgFromDb() {
        showProgress()
        homeViewModel.aboutOrgFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                aboutOtgFromServerObserver()
                GlobalScope.launch {
                    homeViewModel.getAboutOrgFromServer()
                }
            } else {
                hideProgress()

                it[0].Cont_image
                with(binding) {
                    tvAboutOrg.text = it[0].Cont_details.toString().parseAsHtml()
                    if (it[0].Cont_image.isNullOrEmpty()) {
                        cvImage.isVisible = false
                    } else {
                        cvImage.isVisible = true
                        ivImage.load(it[0].Cont_image.toString())
                    }
                }
            }
        })
    }

    private fun aboutOtgFromServerObserver() {
        homeViewModel.aboutOrgFromServer.observe(this, {
            it ?: return@observe
            hideProgress()

            if (it.status.equals("success", true)) {

                if(it.tblAbout==null){
                    binding.tvAboutOrg.text= it.message
                }else{
                    homeViewModel.insert(it.tblAbout!!)
                }

            } else {
                showErrorDialog(
                    message = "माफ गर्नुहोस्!!! सर्भरमा जडान गर्न सकेन \n" +
                            " कृपया पछि फेरि प्रयास गर्नुहोस्",
                    "पुन: प्रयास गर्नुहोस्",
                    "त्रुटि",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            }
        })
    }
}