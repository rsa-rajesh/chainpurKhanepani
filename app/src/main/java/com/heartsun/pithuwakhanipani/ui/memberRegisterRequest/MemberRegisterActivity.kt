package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.heartsun.pithuwakhanipani.databinding.ActivityMemberRegisterBinding
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MemberRegisterActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMemberRegisterBinding.inflate(layoutInflater)
    }
    private val registerViewModel by viewModel<RegisterViewModel>()

    companion object {
        var registerRequest: RegistrationRequest? =
            RegistrationRequest(null, null, null, null, null, null, null, null, null, null)
        fun newIntent(context: Context): Intent {
            return Intent(context, MemberRegisterActivity::class.java)
        }
    }

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    @DelicateCoroutinesApi
    private fun initView() {
        with(binding) {

            toolbar.ivBack.setOnClickListener {
                onBackPressed()
                this@MemberRegisterActivity.finish()
            }
            toolbar.tvToolbarTitle.text = "Member Registration"
        }

        lifecycleScope.launch {
            showProgress()
            delay(1000)
            showProgress()
            registerViewModel.getFileRequirementFromServer()
        }
        rateFromServerObserver()
    }

    private fun rateFromServerObserver() {
        registerViewModel.fileTypesFromServer.observe(this, {
            it ?: return@observe
            registerRequest?.files = it.documentTypes
            hideProgress()
            binding.clError.isVisible = false
        })
    }
}