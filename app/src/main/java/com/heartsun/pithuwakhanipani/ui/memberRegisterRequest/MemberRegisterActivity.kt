package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityMemberRegisterBinding

class MemberRegisterActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMemberRegisterBinding.inflate(layoutInflater)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MemberRegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}