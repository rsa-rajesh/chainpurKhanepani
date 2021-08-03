package com.heartsun.pithuwakhanipani.ui.sameetee

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivitySameeteeListBinding
import com.heartsun.pithuwakhanipani.databinding.ActivitySameeteeSelectionBinding

class SameeteeListActivity : BaseActivity() {

    private val binding by lazy {
        ActivitySameeteeListBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SameeteeListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}