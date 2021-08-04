package com.heartsun.pithuwakhanipani.ui.contact

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityAboutOrganizationBinding
import com.heartsun.pithuwakhanipani.databinding.ActivityContactBinding
import com.heartsun.pithuwakhanipani.ui.noticeBoard.NoticeDetailsActivity

class ContactActivity : BaseActivity() {

    private val binding by lazy {
        ActivityContactBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ContactActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}