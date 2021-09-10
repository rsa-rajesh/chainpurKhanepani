package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidcommon.RDrawable
import androidcommon.base.BaseActivity
import androidcommon.extension.showErrorDialog
import androidx.core.view.isVisible
import com.heartsun.pithuwakhanipani.databinding.ActivityMemberRegisterBinding
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblDocumentType
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

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
        showProgress()
        getFilesRequirementFromDb()
        requestObserver()
    }

    private fun getFilesRequirementFromDb() {

        registerViewModel.fileTypeFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                rateFromServerObserver()
                GlobalScope.launch {
                    registerViewModel.getFileRequirementFromServer()
                }
            } else {
                var docs: MutableList<RegistrationRequest.RequiredDocuments> = arrayListOf()
                for (fileType in it) {
                    var file: RegistrationRequest.RequiredDocuments =
                        RegistrationRequest.RequiredDocuments(fileType.DocTypeName, null)
                    docs.add(file)
                }
                registerRequest?.files = docs
                binding.clError.isVisible = false

                hideProgress()
            }
        })
    }

    private fun rateFromServerObserver() {
        registerViewModel.fileTypesFromServer.observe(this, {
            it ?: return@observe


            if (it.status.equals("success",true)){
                var count: Int = 1
                for (fileType in it.documentTypes) {
                    val file: TblDocumentType = TblDocumentType(count, fileType.DocumentName)
                    count++
                    registerViewModel.insert(fileTypes = file)
                }
            }else {
                hideProgress()
                showErrorDialog(
                    message = "Sorry!!! couldn't connect to the server \n please try again later",
                    "retry",
                    "Error",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            }


        })
    }

    fun requestRegistrationToServer() {
        showProgress()
        thread {
            Thread.sleep(100)
            registerViewModel.sendRegistrationRequestToServer(registerRequest, this)
        }


    }

    private fun requestObserver() {
        registerViewModel.registrationResponse.observe(this, {
            it ?: return@observe
            hideProgress()
            if (it.equals("Success", true)) {
                showErrorDialog(
                    message = "New Tap Request Successfully Submitted",
                    "close",
                    "Success",
                    RDrawable.ic_success_for_dilog,
                    color = Color.GREEN,
                    onBtnClick = {
                        onBackPressed()
                        this.finish()
                    }
                )

            } else {
                hideProgress()
                showErrorDialog(
                    message = "Sorry!!! couldn't complete your request now please try again later ",
                    "retry",
                    "Error",
                    RDrawable.ic_error_for_dilog,
                    color = Color.RED
                )
            }
//            if(it.equals("Success",true)){
//                toastS("New Tap Request Successfully Submitted")
//                var files =  registerRequest?.files
//                registerRequest =RegistrationRequest(null, null, null, null, null, null, null, null, null, files)
//
//                this.findNavController(binding.navHostFragmentContainer.id)
//                    .navigate(R.id.action_membersRegistrationFilesFragment_to_membersRegistrationFormFragment);
//            }
        })
    }

}