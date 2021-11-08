package com.heartsun.pithuwakhanipani.ui.billDetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.toastS
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.databinding.ActivityBillDetailsBinding
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.RegisterViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidcommon.RLayout
import androidcommon.extension.showErrorDialog
import com.google.android.material.chip.Chip
import com.heartsun.pithuwakhanipani.domain.BillDetails
import org.koin.android.ext.android.get
import kotlin.concurrent.thread
import android.app.Activity

import android.view.inputmethod.InputMethodManager


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

            rateFromServerObserver()

            var idsString: String? = prefs.memberIds

            if (!idsString.isNullOrBlank()) {
                var memberIds = prefs.memberIds?.split("-")?.toMutableList()

                if (memberIds != null) {
                    tvCalcuTitle1.isVisible = true
                    cgType.isVisible = true

                    for (id in memberIds) {
                        val chip =
                            layoutInflater.inflate(RLayout.chip_layout, cgType, false) as Chip
                        chip.text = id
                        chip.setOnClickListener {
                            showProgress()
                            etUnit.setText(id)
                            getReport()
//                            registerViewModel.getBillingDetails(etUnit.text.toString().toInt())
                        }
                        cgType.addView(chip)
                    }

                } else {
                    tvCalcuTitle1.isVisible = false
                    cgType.isVisible = false
                }
            } else {
                tvCalcuTitle1.isVisible = false
                cgType.isVisible = false
            }






            toolbar.tvToolbarTitle.text = "बिल विवरण"

            toolbar.ivBack.setOnClickListener {
                super.onBackPressed()
                this@BillDetailsActivity.finish()
            }
            btSubmit.setOnClickListener {
                showProgress()
                hideSoftKeyboard(this@BillDetailsActivity)

                if (!etUnit.text.isNullOrBlank()) {
                    getReport()
                } else {
                    hideProgress()
                    toastS("कृपया तपाईँको दर्ता नम्बर प्रविष्ट गर्नुहोस् ।")
                }
            }
        }

    }


    @SuppressLint("SetTextI18n")
    private fun rateFromServerObserver() {
        registerViewModel.billDetailsFromServer.observe(this, {
            hideProgress()

            it ?: return@observe
//            hideProgress()

            if (it.status.equals("success", false)) {
                if (it.billDetails.isEmpty()) {
                    binding.cvCommunityRate.isVisible = false
                    binding.tvBillDetails.isVisible = false
                    toastS("सदस्यता नम्बर फेला परेन")
                } else {

                    val totalBillDetails: BillDetails = BillDetails(
                        999999, null, 0, null, null, null, 0, 0f, null, null, 0, 0f, 0f, 0f
                    )
                    var bills: MutableList<BillDetails> = arrayListOf()
                    for (billDetails in it.billDetails) {
                        if (billDetails.PaidStatus != 1) {
                            bills.add(billDetails)
                            totalBillDetails.TotReading =
                                totalBillDetails.TotReading?.plus(billDetails.TotReading!!.toInt())
                            totalBillDetails.Amt =
                                totalBillDetails.Amt?.plus(billDetails.Amt!!.toFloat())
                            totalBillDetails.Dis = totalBillDetails.Dis?.plus(billDetails.Dis!!)
                            totalBillDetails.Fine = totalBillDetails.Fine?.plus(billDetails.Fine!!)
                            totalBillDetails.NetAmt =
                                totalBillDetails.NetAmt?.plus(billDetails.NetAmt!!)
                            totalBillDetails.MemberID = billDetails.MemberID
                            totalBillDetails.MemName = billDetails.MemName
                            totalBillDetails.TapNo = billDetails.TapNo
                            totalBillDetails.Address = billDetails.Address
                            totalBillDetails.TapType = billDetails.TapType
                            totalBillDetails.RID = billDetails.RID
                            totalBillDetails.Inv_Date = billDetails.Inv_Date
                            totalBillDetails.Sam_Date = billDetails.Sam_Date
                            totalBillDetails.PaidStatus = billDetails.PaidStatus
                        }
                    }
                    bills.add(totalBillDetails)

                    var idsString: String? = prefs.memberIds

                    if (!idsString.isNullOrBlank()) {
                        var memberIds = prefs.memberIds?.split("-")?.toMutableList()
                        if (memberIds?.contains(
                                it.billDetails[0].MemberID.toString().orEmpty()
                            ) == true
                        ) {
                        } else {
//need to change hear change required
                            memberIds?.reverse()
                            memberIds?.set(5, it.billDetails[0].MemberID.toString().orEmpty())
                            memberIds?.reverse()

                            var ids: String = ""

                            if (memberIds != null) {
                                for (id in memberIds) {
                                    ids = "$ids$id-"
                                }
                            }

                            if (ids.length > 1) {
                                ids = ids.substring(0, ids.lastIndex - 1)
                                prefs.memberIds = ids
                            }
                        }
                    } else {
                        prefs.memberIds = it.billDetails[0].MemberID.toString().orEmpty()
                    }

                    binding.tvName.text = "ग्राहकको नाम :-" + it.billDetails[0].MemName
                    binding.tvAddress.text = "ठेगाना :-" + it.billDetails[0].Address
                    binding.tvDharaNo.text = "धारा न. :-" + it.billDetails[0].TapNo
                    binding.tvDharaType.text = "धाराको प्रकार :-" + it.billDetails[0].TapType

                    binding.cvCommunityRate.isVisible = true
                    binding.tvBillDetails.isVisible = true
                    billDetailsAdapter = BillDetailsAdapter()
                    billDetailsAdapter.items = bills

                    binding.rvCommunityRate.layoutManager = LinearLayoutManager(this)
                    binding.rvCommunityRate.adapter = billDetailsAdapter
                }
            } else {
                hideProgress()
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

    private fun getReport() {
        thread {
            Thread.sleep(100)
            registerViewModel.getBillingDetails(binding.etUnit.text.toString().toInt())
        }
    }

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }
}