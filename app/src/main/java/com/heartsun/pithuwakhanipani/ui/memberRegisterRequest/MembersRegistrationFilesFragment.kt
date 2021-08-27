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
import androidcommon.extension.snackBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.ImageHeaderParser
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    var details: RegistrationRequest? = MemberRegisterActivity.registerRequest

    private fun initViews() {
        filesListAdapter = FilesListAdapter(onAddFileClicked = {
            it.DocImage=MemberRegisterActivity.getFile(it)
        },
            onRemoveFileClicked = {

            })
        filesListAdapter.items = details?.files.orEmpty()
        binding.rvFiles.layoutManager = GridLayoutManager(context, 2)
        binding.rvFiles.adapter = filesListAdapter
    }

}