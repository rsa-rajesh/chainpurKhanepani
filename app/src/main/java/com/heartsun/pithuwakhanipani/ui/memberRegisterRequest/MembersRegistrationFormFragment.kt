package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidcommon.base.BaseFragment
import androidx.navigation.Navigation
import com.heartsun.pithuwakhanipani.R
import com.heartsun.pithuwakhanipani.databinding.FragmentMembersRegisterationFormBinding
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest

class MembersRegistrationFormFragment : BaseFragment<FragmentMembersRegisterationFormBinding>() {


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMembersRegisterationFormBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        binding.apply {
            btNext.setOnClickListener { view ->

                var details: RegistrationRequest = RegistrationRequest(
                    Address = address.text.toString(),
                    MemName = memberName.text.toString(),
                    CitNo = citizenshipNumber.text.toString(),
                    ContactNo = contactNumber.text.toString(),
                    FHName = fatherOrHusbandName.text.toString(),
                    GFILName = grandFatherOrFatherInLawName.text.toString(),
                    MaleCount = maleCount.text.toString(),
                    FemaleCount = femaleCount.text.toString(),
                    Gender = "male",
                    files = null
                )
                validateData(details)

                Navigation.findNavController(view)
                    .navigate(R.id.action_membersRegistrationFormFragment_to_membersRegistrationFilesFragment);
            }
        }
    }

    private fun validateData(details: RegistrationRequest) {

    }
}