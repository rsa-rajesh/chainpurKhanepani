package com.heartsun.pithuwakhanipani.ui.meroKhaniPani.complaint

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import com.heartsun.pithuwakhanipani.databinding.ActivityComplaintBinding
import com.heartsun.pithuwakhanipani.ui.memberRegisterRequest.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ComplaintActivity : BaseActivity() {

    private val registerViewModel by viewModel<RegisterViewModel>()

    private val binding by lazy {
        ActivityComplaintBinding.inflate(layoutInflater)
    }

    companion object {
        private const val memberId = "MemberID"

        fun newIntent(
            context: Context, memberID: String,
        ): Intent {
            return Intent(context, ComplaintActivity::class.java).apply {
                putExtra(memberId, memberID)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}