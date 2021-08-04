package com.heartsun.pithuwakhanipani.ui.about

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityAboutOrganizationBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityNoticeDetailsBinding
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeDetailsActivity

class AboutOrganizationActivity : BaseActivity() {

    private val binding by lazy {
        ActivityAboutOrganizationBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, AboutOrganizationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}