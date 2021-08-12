package com.heartsun.pithuwakhanipani.ui.waterRate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidcommon.base.BaseActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.databinding.ActivityWaterRateBinding
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class WaterRateActivity : BaseActivity() {

    private val binding by lazy {
        ActivityWaterRateBinding.inflate(layoutInflater)
    }
    private lateinit var waterRateAdapter1: WaterRateAdapter
    private lateinit var waterRateAdapter2: WaterRateAdapter
    private val items1 = mutableListOf<TBLReadingSetupDtl>()
    private val items2 = mutableListOf<TBLReadingSetupDtl>()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, WaterRateActivity::class.java)
        }
    }

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private val homeViewModel by viewModel<HomeViewModel>()


    @DelicateCoroutinesApi
    private fun initViews() {
        showProgress()
        getRatesFromLocalDb()
        with(binding) {
            toolbar.tvToolbarTitle.text = "पानीको दर"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@WaterRateActivity.finish()
            }
            cgSize.setOnFocusChangeListener { _, _ ->
                etUnit.setText("")
            }
            cgType.setOnFocusChangeListener { _, _ ->
                etUnit.setText("")
            }
            etUnit.addTextChangedListener {
                if (etUnit.text?.toString()?.length!! > 0) {
                    var rs: Float = 0f

                    clUnit.isVisible = true
                    var unit: Int = etUnit.text.toString().toInt()

                    if (chipPersonal.isChecked) {
                        rs = 0f

                        for (item in items1) {
                            if (item.Amt != 0f) {
                                rs += item.Amt!!
                            } else {

                                if (unit > item.MnNo!! - 1) {
                                    if (unit > (item.MnNo!! - 1) && unit < item.MxNo!!) {
                                        var remUnit = unit - (item.MnNo!! - 1)
                                        rs += (remUnit * item.Rate!!)
                                    } else {
                                        var remUnit = item.MxNo!! - (item.MnNo!! - 1)
                                        rs += (remUnit * item.Rate!!)
                                    }
                                }

                            }
                        }

                    } else {
                        rs = 0f

                        for (item in items2) {
                            if (item.Amt != 0f) {
                                rs += item.Amt!!
                            } else {

                                if (unit > item.MnNo!! - 1) {
                                    if (unit > (item.MnNo!! - 1) && unit < item.MxNo!!) {
                                        var remUnit = unit - (item.MnNo!! - 1)
                                        rs += (remUnit * item.Rate!!)
                                    } else {
                                        var remUnit = item.MxNo!! - (item.MnNo!! - 1)
                                        rs += (remUnit * item.Rate!!)
                                    }
                                }

                            }
                        }
                    }

                    tvAmount.text = rs.toString()


                } else {
                    clUnit.isVisible = false
                }
            }

        }
    }

    private fun getRatesFromLocalDb() {
        homeViewModel.ratesFromLocalDb.observe(this, {
            it ?: return@observe

            if (it.isNullOrEmpty()) {
                rateFromServerObserver()
                GlobalScope.launch {
                    homeViewModel.getRatesFromServer("test")
                }
            } else {
                hideProgress()
                for (item in it) {
                    if (item.VNO == 1) {
                        items1.add(item)
                    } else {
                        items2.add(item)
                    }
                }

                if (!items1.isNullOrEmpty()) {
                    binding.cvPersonalRate.isVisible = true
                    waterRateAdapter1 = WaterRateAdapter()
                    waterRateAdapter1.items = items1
                    binding.rvPersonalRate.layoutManager = LinearLayoutManager(this)
                    binding.rvPersonalRate.adapter = waterRateAdapter1
                } else {
                    binding.cvPersonalRate.isVisible = false
                }


                if (!items1.isNullOrEmpty()) {
                    binding.cvCommunityRate.isVisible = true
                    waterRateAdapter2 = WaterRateAdapter()
                    waterRateAdapter2.items = items2
                    binding.rvCommunityRate.layoutManager = LinearLayoutManager(this)
                    binding.rvCommunityRate.adapter = waterRateAdapter1
                } else {
                    binding.cvCommunityRate.isVisible = false
                }
            }


        })
    }

    private fun rateFromServerObserver() {
        homeViewModel.ratesFromServer.observe(this, {
            it ?: return@observe

            for (tapType in it.tapType) {
                homeViewModel.insert(tapType)
            }
            for (tapType in it.readingSetupDetails) {
                homeViewModel.insert(tapType)
            }
            for (tapType in it.readingSetup) {
                homeViewModel.insert(tapType)
            }
        })
    }

}