package com.heartsun.pithuwakhanipani.ui.noticeBoard

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityNoticeBoardBinding
import com.heartsun.pithuwakhanipani.databinding.ActivitySameeteeSelectionBinding
import com.heartsun.pithuwakhanipani.ui.sameetee.SameeteeSelectionActivity

class NoticeBoardActivity : BaseActivity() {

    private val binding by lazy {
        ActivityNoticeBoardBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NoticeBoardActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}