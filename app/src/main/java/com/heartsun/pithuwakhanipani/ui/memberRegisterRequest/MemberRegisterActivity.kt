package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidx.core.view.isVisible
import com.heartsun.pithuwakhanipani.databinding.ActivityMemberRegisterBinding
import com.heartsun.pithuwakhanipani.domain.DocumentTypesResponse
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemberRegisterActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMemberRegisterBinding.inflate(layoutInflater)
    }
    private val registerViewModel by viewModel<RegisterViewModel>()

    companion object {

        lateinit var registerRequest:RegistrationRequest
        fun newIntent(context: Context): Intent {
            return Intent(context, MemberRegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        rateFromServerObserver()
        showProgress()
        registerViewModel.getFileRequirementFromServer()
    }

    private fun rateFromServerObserver() {
        registerViewModel.fileTypesFromServer.observe(this, {
            it ?: return@observe
            registerRequest.files = it.documentTypes
            hideProgress()
            binding.clError.isVisible=false
        })
    }
}