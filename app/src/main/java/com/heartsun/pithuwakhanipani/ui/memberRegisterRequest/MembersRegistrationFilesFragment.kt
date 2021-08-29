package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidcommon.base.BaseFragment
import androidcommon.extension.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.ImageHeaderParser
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.databinding.BottomSheetImageSelectionBinding
import com.heartsun.pithuwakhanipani.databinding.FragmentMembersRegisterationFilesBinding
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import com.heartsun.pithuwakhanipani.ui.contact.ContactListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel


class MembersRegistrationFilesFragment : BaseFragment<FragmentMembersRegisterationFilesBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMembersRegisterationFilesBinding.inflate(inflater, container, false)

    private lateinit var filesListAdapter: FilesListAdapter
    private val registerViewModel by viewModel<RegisterViewModel>()
    private var whichPermission = Manifest.permission.INTERNET
    private var onPermissionGranted: () -> Unit = {}

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private var indexOfImage: Int = -1;

    var details: RegistrationRequest? = MemberRegisterActivity.registerRequest

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        filesListAdapter = FilesListAdapter(onAddFileClicked = {
            indexOfImage = it
            openPhotoActions()
        },
            onRemoveFileClicked = {
                details?.files?.get(it)?.DocImage = null
                filesListAdapter.items = details?.files.orEmpty()
                filesListAdapter.notifyItemChanged(it)
            })
        filesListAdapter.items = details?.files.orEmpty()
        binding.rvFiles.layoutManager = GridLayoutManager(context, 2)
        binding.rvFiles.adapter = filesListAdapter
        binding.btSubmit.setOnClickListener {
            validateFiles()
        }
    }

    private fun validateFiles() {
        var boolean: Boolean = true

        for (data in details?.files.orEmpty()) {
            if (data.DocImage.isNullOrEmpty()) {
                boolean = false
            }
        }

        if (boolean){


            registerViewModel.sendRegistrationRequestToServer(details)

        }else{
            toastS("कृपया सबै आवश्यक फाइलहरू छनोट गर्नुहोस् ।")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    onPermissionGranted.invoke()
                }
                shouldShowRequestPermissionRationale(whichPermission) -> {
                    context?.dialogShowRationale { askPermissionAgain() }
                }
                else -> {
                    context?.dialogOnForeverDenied { context?.openSettings() }
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.M)
    open fun askPermission(whichPermission: String, onPermissionGranted: () -> Unit) {
        this.whichPermission = whichPermission
        this.onPermissionGranted = onPermissionGranted
        if (context?.hasPermission(whichPermission) == true) {
            onPermissionGranted.invoke()
        } else {
            requestPermissionLauncher.launch(whichPermission)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun askPermissionAgain() {
        requestPermissionLauncher.launch(whichPermission)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun openPhotoActions() {
        val bottomSheet = context?.let { BottomSheetDialog(it, R.style.BottomSheetDialog) }
        val bottomSheetView = BottomSheetImageSelectionBinding.inflate(layoutInflater, null, false)
        bottomSheet?.setContentView(bottomSheetView.root)
        with(bottomSheetView) {
            clCamera.setOnClickListener {
                askPermission(Manifest.permission.CAMERA) {
                    // takePicture.launch(updateViewModel.getUriToSaveImage())
                    pickFromCamera()
                }
                bottomSheet?.dismiss()
            }
            clGallery.setOnClickListener {
                askPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                    // pickImage.launch("image/*")
                    pickFromGallery()
                }
                bottomSheet?.dismiss()
            }
        }
        bottomSheet?.show()
    }

    private fun pickFromCamera() {
        showProgress()
        ImagePicker.with(this)
            .compress(1024)
            .cameraOnly()
            .createIntent {
                startImagePicker.launch(it)
            }
    }

    private fun pickFromGallery() {
        showProgress()
        ImagePicker.with(this)
            .compress(1024)
            .galleryOnly()
            .createIntent {
                startImagePicker.launch(it)
            }
    }

    private val startImagePicker =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val result = activityResult.data
            when (activityResult.resultCode) {
                AppCompatActivity.RESULT_OK -> {
                    val displayOnUi = result?.data
                    hideProgress()
                    if (indexOfImage != -1) {
                        details?.files?.get(indexOfImage)?.DocImage = displayOnUi.toString()
                        filesListAdapter.items = details?.files.orEmpty()
                        filesListAdapter.notifyItemChanged(indexOfImage)

                    }

//                    setImageUri(displayOnUi)
                }
                ImagePicker.RESULT_ERROR -> {
                    toastS(ImagePicker.getError(result))
                }
                else -> {
                    toastS("Task Cancelled")
                }
            }
        }
}


