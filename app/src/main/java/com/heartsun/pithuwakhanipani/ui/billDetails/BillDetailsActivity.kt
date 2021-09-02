package com.heartsun.pithuwakhanipani.ui.billDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidcommon.extension.toastS
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.databinding.ActivityBillDetailsBinding
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.MemberRegisterActivity
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.RegisterViewModel
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BillDetailsActivity : BaseActivity() {

    private val binding by lazy {
        ActivityBillDetailsBinding.inflate(layoutInflater)
    }

    private val registerViewModel by viewModel<RegisterViewModel>()

    private lateinit var billDetailsAdapter: BillDetailsAdapter

    private val prefs by inject<Prefs>()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BillDetailsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        with(binding) {

            btSubmit.setOnClickListener {
                if (!etUnit.text.isNullOrBlank()) {
                    registerViewModel.getBillingDetails(etUnit.text.toString().toInt())
                } else {
                    toastS("कृपया तपाईँको दर्ता नम्बर प्रविष्ट गर्नुहोस् ।")
                }
            }
        }

    }


    private fun rateFromServerObserver() {
        registerViewModel.billDetailsFromServer.observe(this, {
            it ?: return@observe
            billDetailsAdapter = BillDetailsAdapter()
            billDetailsAdapter.items = it.billDetails
            binding.rvCommunityRate.layoutManager = LinearLayoutManager(this)
            binding.rvCommunityRate.adapter = billDetailsAdapter
        })
    }

}