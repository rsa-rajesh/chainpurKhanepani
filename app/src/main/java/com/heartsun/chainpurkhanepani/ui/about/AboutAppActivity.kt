package com.heartsun.chainpurkhanepani.ui.about

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.chainpurkhanepani.R
import com.heartsun.chainpurkhanepani.databinding.ActivityAboutAppBinding

class AboutAppActivity : BaseActivity() {

    private val binding by lazy {
        ActivityAboutAppBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AboutAppActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initViews()

    }

    private fun initViews() {
        with(binding) {
            ivBack.setOnClickListener {
                onBackPressed()
                this@AboutAppActivity.finish()
            }
            val version = getString(R.string.app_version)
            tvAppVersion.text = "version $version"
        }
    }
}