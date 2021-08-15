package com.heartsun.pithuwakhanipani.ui

import androidx.lifecycle.*
import com.heartsun.pithuwakhanipani.data.repository.AuthRepository
import com.heartsun.pithuwakhanipani.data.repository.databaseReppo.DbRepository
import com.heartsun.pithuwakhanipani.domain.MembersListResponse
import com.heartsun.pithuwakhanipani.domain.WaterRateListResponse
import com.heartsun.pithuwakhanipani.domain.dbmodel.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class HomeViewModel(
    private val dbRepository: DbRepository,
    private val homeRepository: AuthRepository
) : ViewModel() {

    private val _rates = MutableLiveData<WaterRateListResponse>()
    val ratesFromServer: LiveData<WaterRateListResponse> = _rates
    fun getRatesFromServer(status: String) {
        viewModelScope.launch {
            _rates.value = homeRepository.getRates(status = status)
        }
    }

    fun insert(shipment: TblTapTypeMaster) = viewModelScope.launch {
        dbRepository.insert(tblTapTypeMaster = shipment)
    }

    fun insert(shipment: TBLReadingSetupDtl) = viewModelScope.launch {
        dbRepository.insert(tBLReadingSetupDtl = shipment)
    }

    fun insert(shipment: TBLReadingSetup) = viewModelScope.launch {
        dbRepository.insert(tblReadingSetup = shipment)
    }

    fun deleteAllLocal() = viewModelScope.launch {
        dbRepository.deleteAll()
    }

    val ratesFromLocalDb: LiveData<List<TBLReadingSetupDtl>> =
        dbRepository.tBLReadingSetupDtls.asLiveData()

    //for members activity


    private val _members = MutableLiveData<MembersListResponse>()
    val membersFromServer: LiveData<MembersListResponse> = _members
    fun getMembersFromServer() {
        viewModelScope.launch {
            _members.value = homeRepository.getMembers()
        }
    }


    var membersFromLocal: LiveData<List<TblContact>>? = null
    fun getMembers(memberTypeId: Int) = viewModelScope.launch {
        membersFromLocal = dbRepository.getContactList(memberTypeId).asLiveData()
    }

    //    var oldMembersFromLocal: LiveData<List<TblContact>>? = null
    fun getOldMembers(memberTypeId: Int) = viewModelScope.launch {
        membersFromLocal = dbRepository.getOldContactList(memberTypeId).asLiveData()
    }

    val membersTypeFromLocalDb: LiveData<List<TblBoardMemberType>> =
        dbRepository.tblBoardMemberTypes.asLiveData()

    fun insert(memberType: TblBoardMemberType) = viewModelScope.launch {
        dbRepository.insert(tblBoardMemberTypes = memberType)
    }

    fun insert(members: TblContact) = viewModelScope.launch {
        dbRepository.insert(tblContacts = members)
    }
}