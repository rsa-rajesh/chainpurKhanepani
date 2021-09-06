package com.heartsun.pithuwakhanipani.ui.meroKhaniPani.ledger

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.RLayout
import androidcommon.base.BaseActivity
import androidcommon.extension.toastS
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.databinding.ActivityBillDetailsBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityLedgerBinding
import com.heartsun.pithuwakhanipani.domain.BillDetails
import com.heartsun.pithuwakhanipani.ui.billDetails.BillDetailsActivity
import com.heartsun.pithuwakhanipani.ui.billDetails.BillDetailsAdapter
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.RegisterViewModel
import com.heartsun.pithuwakhanipani.ui.meroKhaniPani.personalMenu.PersonalMenu
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

class LedgerActivity : BaseActivity() {

    private val binding by lazy {
        ActivityLedgerBinding.inflate(layoutInflater)
    }

    private val memberID by lazy {
        intent.getStringExtra(memberId)
    }

    private val registerViewModel by viewModel<RegisterViewModel>()

    private lateinit var billDetailsAdapter: BillDetailsAdapter


    companion object {
        private const val memberId = "MemberID"

        fun newIntent(
            context: Context, memberID: String,
        ): Intent {
            return Intent(context, LedgerActivity::class.java).apply {
                putExtra(LedgerActivity.memberId, memberID)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        with(binding) {
            rateFromServerObserver()
            getReport()

            toolbar.tvToolbarTitle.text = "लेजर"

            toolbar.ivBack.setOnClickListener {
                super.onBackPressed()
                this@LedgerActivity.finish()
            }

        }

    }


    @SuppressLint("SetTextI18n")
    private fun rateFromServerObserver() {
        registerViewModel.billDetailsFromServer.observe(this, {

            it ?: return@observe
//            hideProgress()
            if (it.billDetails.isEmpty()) {
                binding.cvCommunityRate.isVisible = false
                toastS("सदस्यता नम्बर फेला परेन")
            } else {


                var bills: MutableList<BillDetails> = arrayListOf()
                for (billDetails in it.billDetails) {
                    if (billDetails.PaidStatus != 1) {
                        bills.add(billDetails)

                    }

                }

                binding.tvName.text = "ग्राहकको नाम :-" + it.billDetails[0].MemName
                binding.tvAddress.text = "ठेगाना :-" + it.billDetails[0].Address
                binding.tvDharaNo.text = "धारा न. :-" + it.billDetails[0].TapNo
                binding.tvDharaType.text = "धाराको प्रकार :-" + it.billDetails[0].TapType

                binding.cvCommunityRate.isVisible = true
                billDetailsAdapter = BillDetailsAdapter()
                billDetailsAdapter.items = it.billDetails
//                billDetailsAdapter.items = bills

                binding.rvCommunityRate.layoutManager = LinearLayoutManager(this)
                binding.rvCommunityRate.adapter = billDetailsAdapter
            }

            hideProgress()

        })
    }

    private fun getReport() {
        showProgress()
        thread {
            Thread.sleep(100)
            registerViewModel.getBillingDetails(memberId = memberID!!.toInt())
        }
    }
}