package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heartsun.pithuwakhanipani.data.repository.AuthRepository
import com.heartsun.pithuwakhanipani.data.repository.databaseReppo.DbRepository
import com.heartsun.pithuwakhanipani.domain.DocumentTypesResponse
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import kotlinx.coroutines.launch
import java.nio.file.Files

class RegisterViewModel(
    private val dbRepository: DbRepository,
    private val homeRepository: AuthRepository
) : ViewModel() {

    private val _filesRequirements = MutableLiveData<DocumentTypesResponse>()
    val fileTypesFromServer: LiveData<DocumentTypesResponse> = _filesRequirements
    fun getFileRequirementFromServer() {
        viewModelScope.launch {
            _filesRequirements.value = homeRepository.getFilesRequirement()
        }
    }


}