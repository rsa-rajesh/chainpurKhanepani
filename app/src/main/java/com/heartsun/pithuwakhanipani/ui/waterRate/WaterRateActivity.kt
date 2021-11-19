package com.heartsun.pithuwakhanipani.ui.waterRate

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.RLayout
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
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
    private var allItems = mutableListOf<TBLReadingSetupDtl>()
    private var selectedItems = mutableListOf<TBLReadingSetupDtl>()

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
        getTapTypeFromLocalDb()
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

//                    if (chipPersonal.isChecked) {
                        rs = 0f

                        for (item in selectedItems) {
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

//                    }
//                    else {
//                        rs = 0f
//
//                        for (item in items2) {
//                            if (item.Amt != 0f) {
//                                rs += item.Amt!!
//                            } else {
//
//                                if (unit > item.MnNo!! - 1) {
//                                    if (unit > (item.MnNo!! - 1) && unit < item.MxNo!!) {
//                                        var remUnit = unit - (item.MnNo!! - 1)
//                                        rs += (remUnit * item.Rate!!)
//                                    } else {
//                                        var remUnit = item.MxNo!! - (item.MnNo!! - 1)
//                                        rs += (remUnit * item.Rate!!)
//                                    }
//                                }
//
//                            }
//                        }
//                    }

                    tvAmount.text = rs.toString()


                } else {
                    clUnit.isVisible = false
                }
            }

        }
    }

    private fun getTapTypeFromLocalDb() {
        homeViewModel.tapTypesObserver.observe(this, {
            it ?: return@observe
            hideProgress()
            if (it.isNullOrEmpty()) {
                rateFromServerObserver()
                GlobalScope.launch {
                    homeViewModel.getRatesFromServer("test")
                }
            } else {

                for (types in it) {
                    val chip =
                        layoutInflater.inflate(RLayout.chip_layout, binding.cgType, false) as Chip
                    chip.text = types.TapTypeName
                    chip.setOnClickListener {
                        binding.tvListTitle1.text=types.TapTypeName

                        selectedItems.clear()
                        for (items in allItems){
                            if(items.VNO==types.TapTypeID){
                                selectedItems.add(items)
                            }
                        }
//                        selectedItems=types
//                        showProgress()
//                        etUnit.setText(id)
//                        getReport()
//                            registerViewModel.getBillingDetails(etUnit.text.toString().toInt())
                    }
                    binding.cgType.addView(chip)
                }

                binding.cgType.check(binding.cgType[0].id)

                var chip:Chip = findViewById(binding.cgType[0].id)


                binding.tvListTitle1.text=chip.text


            }


        })
    }

    private fun getRatesFromLocalDb() {
        homeViewModel.ratesFromLocalDb.observe(this, {
            it ?: return@observe

            hideProgress()

            if (it.isNullOrEmpty()) {
                rateFromServerObserver()
                GlobalScope.launch {
                    homeViewModel.getRatesFromServer("test")
                }
            } else {
                hideProgress()
                allItems.clear()
                selectedItems.clear()

                allItems= it as MutableList<TBLReadingSetupDtl>

                for (item in it) {
                    if (item.VNO == 1) {
                        selectedItems.add(item)
                    }
                }

                if (!selectedItems.isNullOrEmpty()) {
                    binding.rvCommunityRate.isVisible = true
                    waterRateAdapter1 = WaterRateAdapter()
                    waterRateAdapter1.items = selectedItems
                    binding.rvCommunityRate.layoutManager = LinearLayoutManager(this)
                    binding.rvCommunityRate.adapter = waterRateAdapter1
                } else {
                    binding.rvCommunityRate.isVisible = false
                }


//                if (!items1.isNullOrEmpty()) {
//                    binding.cvCommunityRate.isVisible = true
//                    waterRateAdapter2 = WaterRateAdapter()
//                    waterRateAdapter2.items = items2
//                    binding.rvCommunityRate.layoutManager = LinearLayoutManager(this)
//                    binding.rvCommunityRate.adapter = waterRateAdapter1
//                } else {
//                    binding.cvCommunityRate.isVisible = false
//                }
            }


        })
    }

    private fun rateFromServerObserver() {
        homeViewModel.ratesFromServer.observe(this, {
            it ?: return@observe

            if(it.status.equals("Success",true)){

                for (tapType in it.tapType) {
                    homeViewModel.insert(tapType)
                }
                for (tapType in it.readingSetupDetails) {
                    homeViewModel.insert(tapType)
                }
                for (tapType in it.readingSetup) {
                    homeViewModel.insert(tapType)
                }
            }else{
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

}