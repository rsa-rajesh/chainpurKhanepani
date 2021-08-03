package com.heartsun.pithuwakhanipani.ui.waterRate

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityHomeBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityWaterRateBinding
import com.heartsun.pithuwakhanipani.ui.HomeActivity

class WaterRateActivity : BaseActivity() {

    private val binding by lazy {
        ActivityWaterRateBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, WaterRateActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding) {
            toolbar.tvToolbarTitle.text = "पानीको दर"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@WaterRateActivity.finish()
            }
        }
    }
}