package com.heartsun.pithuwakhanipani.ui.sameetee

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivitySameeteeSelectionBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityWaterRateBinding
import com.heartsun.pithuwakhanipani.ui.waterRate.WaterRateActivity

class SameeteeSelectionActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySameeteeSelectionBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SameeteeSelectionActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            toolbar.tvToolbarTitle.text = "समिति"
            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@SameeteeSelectionActivity.finish()
            }
        }
    }
}