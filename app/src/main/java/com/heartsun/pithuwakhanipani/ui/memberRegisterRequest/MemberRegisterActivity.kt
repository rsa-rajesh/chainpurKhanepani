package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidcommon.extension.toastS
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.databinding.ActivityMemberRegisterBinding
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblDocumentType
import com.heartsun.pithuwakhanipani.ui.sameetee.MemberTypeAdapter
import com.heartsun.pithuwakhanipani.ui.sameetee.SameeteeListActivity
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
        getFilesRequirementFromDb()
        requestObserver()
    }

    private fun getFilesRequirementFromDb() {
        showProgress()

        registerViewModel.fileTypeFromLocalDb.observe(this, { it ->
            it ?: return@observe
            if (it.isNullOrEmpty()) {
                rateFromServerObserver()
                GlobalScope.launch {
                    registerViewModel.getFileRequirementFromServer()
                }
            } else {
                var docs:MutableList<RegistrationRequest.RequiredDocuments> = arrayListOf()
                for (fileType in  it){
                    var file: RegistrationRequest.RequiredDocuments = RegistrationRequest.RequiredDocuments(fileType.DocTypeName,null)
                    docs.add(file)
                }
                registerRequest?.files = docs
                binding.clError.isVisible = false

                hideProgress()
            }
        })    }

    private fun rateFromServerObserver() {
        registerViewModel.fileTypesFromServer.observe(this, {
            it ?: return@observe
            var count:Int=1
            for (fileType in it.documentTypes) {
               val file:TblDocumentType= TblDocumentType(count,fileType.DocumentName)
                count++
                registerViewModel.insert(fileTypes = file)
            }

        })
    }

    fun requestRegistrationToServer(){
        showProgress()
        registerViewModel.sendRegistrationRequestToServer(registerRequest,this)
    }

    private fun requestObserver() {
        registerViewModel.registrationResponse.observe(this, {
            it ?: return@observe
            if(it.equals("Success",true)){
                toastS("New Tap Request Successfully Submitted")

                var files =  registerRequest?.files

                registerRequest =RegistrationRequest(null, null, null, null, null, null, null, null, null, files)

                Navigation.findNavController(binding.btDownloadForm)
                    .navigate(R.id.action_membersRegistrationFilesFragment_to_membersRegistrationFormFragment);
            }
        })
    }

}