package com.heartsun.pithuwakhanipani.ui.about

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.RString
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityAboutAppBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityAboutOrganizationBinding
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeDetailsActivity

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
            tvAppVersion.text = "version ${RString.app_version}"
        }
    }
}