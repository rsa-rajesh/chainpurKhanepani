package com.heartsun.pithuwakhanipani.ui.meroKhaniPani

import androidx.lifecycle.*
import com.heartsun.pithuwakhanipani.data.repository.AuthRepository
import com.heartsun.pithuwakhanipani.data.repository.databaseReppo.DbRepository
import com.heartsun.pithuwakhanipani.domain.*
import com.heartsun.pithuwakhanipani.domain.dbmodel.*
import kotlinx.coroutines.launch

class MyTapViewModel(
    private val dbRepository: DbRepository,
    private val homeRepository: AuthRepository
) : ViewModel() {

    private val _addTap = MutableLiveData<UserDetailsResponse>()
    val addTap: LiveData<UserDetailsResponse> = _addTap
    fun addTap(phoneNo: String, pin: String) {
        viewModelScope.launch {
            _addTap.value = homeRepository.getUserDetails(phoneNo, pin)
        }
    }

    private val _pinRequest = MutableLiveData<String>()
    val pinRequest: LiveData<String> = _pinRequest
    fun requestPin(phoneNo: String, memberId: String) {
        viewModelScope.launch {
            _pinRequest.value = homeRepository.requestPin(phoneNo, memberId)
        }
    }

    fun insert(members: TblMember) = viewModelScope.launch {
        dbRepository.insert(members)
    }

    fun delete(members: TblMember) = viewModelScope.launch {
        dbRepository.delete(members.MemberID)
    }


    val tapsListFromLocalDb: LiveData<List<TblMember>> = dbRepository.getAllTaps.asLiveData()


//    val noticesFromLocalDb: LiveData<List<TblNotice>> =
//        dbRepository.tblNotices.asLiveData()
}