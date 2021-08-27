package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidcommon.base.BaseActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.databinding.ActivityMemberRegisterBinding
import com.heartsun.pithuwakhanipani.databinding.BottomSheetImageSelectionBinding
import com.heartsun.pithuwakhanipani.domain.DocumentTypesResponse
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

        fun getFile(it: RegistrationRequest.RequiredDocuments): Uri? {

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


    @RequiresApi(Build.VERSION_CODES.M)
    private fun openPhotoActions() {
        val bottomSheet = BottomSheetDialog(this, R.style.BottomSheetDialog)
        val bottomSheetView = BottomSheetImageSelectionBinding.inflate(layoutInflater, null, false)
        bottomSheet.setContentView(bottomSheetView.root)
        with(bottomSheetView) {
            clCamera.setOnClickListener {
                askPermission(Manifest.permission.CAMERA) {
                    takePicture.launch(registerViewModel.getUriToSaveImage())
                }
                bottomSheet.dismiss()
            }
            clGallery.setOnClickListener {
                askPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                    pickImage.launch("image/*")
                }
                bottomSheet.dismiss()
            }
        }
        bottomSheet.show()
    }

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { photoSaved ->
            if (photoSaved) {
                setImageUri(registerViewModel.getUriToSaveImage())
            }
        }

    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            setImageUri(uri)
        }


}