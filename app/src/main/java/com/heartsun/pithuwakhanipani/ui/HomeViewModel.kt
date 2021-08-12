package com.heartsun.pithuwakhanipani.ui

import androidx.lifecycle.*
import com.heartsun.pithuwakhanipani.data.repository.AuthRepository
import com.heartsun.pithuwakhanipani.data.repository.databaseReppo.DbRepository
import com.heartsun.pithuwakhanipani.domain.WaterRateListResponse
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetup
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblTapTypeMaster
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
}