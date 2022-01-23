package com.heartsun.chainpurkhanepani.ui.memberRegisterRequest

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidcommon.extension.loggerE
import androidx.lifecycle.*
import com.heartsun.chainpurkhanepani.data.repository.AuthRepository
import com.heartsun.chainpurkhanepani.data.repository.databaseReppo.DbRepository
import com.heartsun.chainpurkhanepani.domain.*
import com.heartsun.chainpurkhanepani.domain.dbmodel.TblDocumentType
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
            loggerE("calling data", "specTest")
            val a: String? = homeRepository.sendRegistrationRequest(details,context)
            if (a != null) {
                Handler(Looper.getMainLooper()).post(Runnable {
                    _sendRegistrationRequest.value = a!!
                })
            }
        }
    }


    val fileTypeFromLocalDb: LiveData<List<TblDocumentType>> =
        dbRepository.files.asLiveData()

    fun insert(fileTypes: TblDocumentType) = viewModelScope.launch {
        dbRepository.insert(contacts = fileTypes)
    }


    private val _billDetails = MutableLiveData<BillDetailsResponse>()
    val billDetailsFromServer: LiveData<BillDetailsResponse> = _billDetails
    fun getBillingDetails(memberId:Int) {
        viewModelScope.launch {
            _billDetails.value = homeRepository.getBillDetails(memberId)
        }
    }

    private val _ledgerDetails = MutableLiveData<LedgerDetailsResponse>()
    val ledgerDetailsFromServer: LiveData<LedgerDetailsResponse> = _ledgerDetails
    fun getLedgerDetails(memberId:Int) {
        viewModelScope.launch {
            _ledgerDetails.value = homeRepository.getLedgerDetails(memberId)
        }
    }
}