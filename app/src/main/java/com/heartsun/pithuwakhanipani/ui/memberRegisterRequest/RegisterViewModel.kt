package com.heartsun.pithuwakhanipani.ui.memberRegisterRequest

import android.content.Context
import androidx.lifecycle.*
import com.heartsun.pithuwakhanipani.data.repository.AuthRepository
import com.heartsun.pithuwakhanipani.data.repository.databaseReppo.DbRepository
import com.heartsun.pithuwakhanipani.domain.DocumentTypesResponse
import com.heartsun.pithuwakhanipani.domain.RegistrationRequest
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblBoardMemberType
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblDepartmentContact
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblDocumentType
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val dbRepository: DbRepository,
    private val homeRepository: AuthRepository,

) : ViewModel() {
    private val _filesRequirements = MutableLiveData<DocumentTypesResponse>()
    val fileTypesFromServer: LiveData<DocumentTypesResponse> = _filesRequirements
    fun getFileRequirementFromServer() {
        viewModelScope.launch {
            _filesRequirements.value = homeRepository.getFilesRequirement()
        }
    }

    private val _sendRegistrationRequest = MutableLiveData<String>()
    val registrationResponse: LiveData<String> = _sendRegistrationRequest
    fun sendRegistrationRequestToServer(details: RegistrationRequest?,context:Context) {
        viewModelScope.launch {
            _sendRegistrationRequest.value = homeRepository.sendRegistrationRequest(details,context)
        }
    }


    val fileTypeFromLocalDb: LiveData<List<TblDocumentType>> =
        dbRepository.files.asLiveData()

    fun insert(fileTypes: TblDocumentType) = viewModelScope.launch {
        dbRepository.insert(contacts = fileTypes)
    }
}