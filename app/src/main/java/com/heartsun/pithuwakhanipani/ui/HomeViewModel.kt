package com.heartsun.pithuwakhanipani.ui

import androidx.lifecycle.*
import com.heartsun.pithuwakhanipani.data.repository.AuthRepository
import com.heartsun.pithuwakhanipani.data.repository.databaseReppo.DbRepository
import com.heartsun.pithuwakhanipani.domain.*
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


    private val _notices = MutableLiveData<NoticesListResponse>()
    val noticesFromServer: LiveData<NoticesListResponse> = _notices
    fun getNoticesFromServer() {
        viewModelScope.launch {
            _notices.value = homeRepository.getNotices()
        }
    }

    fun insert(notice: TblNotice) = viewModelScope.launch {
        dbRepository.insert(notice)
    }

    val noticesFromLocalDb: LiveData<List<TblNotice>> =
        dbRepository.tblNotices.asLiveData()


    private val _aboutOrg = MutableLiveData<AboutOrgResponse>()
    val aboutOrgFromServer: LiveData<AboutOrgResponse> = _aboutOrg
    fun getAboutOrgFromServer() {
        viewModelScope.launch {
            _aboutOrg.value = homeRepository.getAboutOrg()
        }
    }

    fun insert(about: TblAboutOrg) = viewModelScope.launch {
        dbRepository.insert(about)
    }

    val aboutOrgFromLocalDb: LiveData<List<TblAboutOrg>> =
        dbRepository.about.asLiveData()

//contacts

    val contactsListFromLocalDb: LiveData<List<TblDepartmentContact>> =
        dbRepository.contact.asLiveData()

    fun insert(contacts: TblDepartmentContact) = viewModelScope.launch {
        dbRepository.insert(contacts = contacts)
    }


    private val _contacts = MutableLiveData<ContactsListResponse>()
    val contactsFromServer: LiveData<ContactsListResponse> = _contacts
    fun getContactsFromServer() {
        viewModelScope.launch {
            _contacts.value = homeRepository.getContacts()
        }
    }
}