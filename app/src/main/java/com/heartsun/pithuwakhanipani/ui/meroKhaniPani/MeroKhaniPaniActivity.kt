package com.heartsun.pithuwakhanipani.ui.meroKhaniPani

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidcommon.extension.showAddTapDialog
import androidcommon.extension.showRequestPinDialog
import androidcommon.extension.toastS
import com.heartsun.pithuwakhanipani.databinding.ActivityHomeBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityMeroKhaniPaniBinding
import com.heartsun.pithuwakhanipani.ui.HomeActivity
import com.heartsun.pithuwakhanipani.ui.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MeroKhaniPaniActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMeroKhaniPaniBinding.inflate(layoutInflater)
    }

    private val myTapViewModel by viewModel<MyTapViewModel>()

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MeroKhaniPaniActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            toolbar.tvToolbarTitle.text = "मेरो खानेपानी"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@MeroKhaniPaniActivity.finish()
            }
            listOf(btAdd, btAddNew).forEach { it ->
                it.setOnClickListener {
                    openAddDialog()
                }
            }
        }

    }

    private fun openRequestDialog() {
        showRequestPinDialog(onAddClick = {
            openAddDialog()
        }, onRequestClick = { phoneNo, memberId ->
            requestNewPin(phoneNo, memberId)
        })
    }

    private fun openAddDialog() {
        showAddTapDialog(onAddClick = { phoneNo, pin ->
            addTap(phoneNo, pin)
        }, onRequestClick = {
            openRequestDialog()
        })
    }

    private fun addTap(phoneNo: String, pin: String) {
        toastS("phone:-$phoneNo pin:-$pin")
        myTapViewModel.addTap(phoneNo, pin)
    }

    private fun requestNewPin(phoneNo: String, memberId: String) {
        toastS("phone:-$phoneNo pin:-$memberId")

        myTapViewModel.requestPin(phoneNo, memberId)
    }

}