package com.heartsun.pithuwakhanipani.data.repository.databaseReppo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heartsun.pithuwakhanipani.data.dao.*
import com.heartsun.pithuwakhanipani.data.database.KanipaniDatabase
import com.heartsun.pithuwakhanipani.domain.dbmodel.*
import kotlinx.coroutines.flow.Flow

class DbRepository(database: KanipaniDatabase) {



    //for tap types and rates -- starts

    private var tblReadingSetupDtlDao: TBLReadingSetupDtlDao = database.tBLReadingSetupDtlDao()
    private var tblReadingSetupDao: TBLReadingSetupDao = database.tBLReadingSetupDao()
    private var tblTapTypeMasterDao: TblTapTypeMasterDao = database.tblTapTypeMasterDao()

    val tBLReadingSetupDtls: Flow<List<TBLReadingSetupDtl>> =
        tblReadingSetupDtlDao.getAllData()

    suspend fun insert(tBLReadingSetupDtl: TBLReadingSetupDtl) {
        tblReadingSetupDtlDao.insert(table = tBLReadingSetupDtl)
    }

    suspend fun deleteAll(tBLReadingSetupDtl: TBLReadingSetupDtl) {
        tblReadingSetupDtlDao.deleteAll()
    }


    val tBLReadingSetup: Flow<List<TBLReadingSetup>> =
        tblReadingSetupDao.getAllData()

    suspend fun insert(tblReadingSetup: TBLReadingSetup) {
        tblReadingSetupDao.insert(table = tblReadingSetup)
    }

    suspend fun deleteAll(tblReadingSetupDao: TBLReadingSetup) {
        tblReadingSetupDtlDao.deleteAll()
    }


    val tblTapTypeMaster: Flow<List<TblTapTypeMaster>> =
        tblTapTypeMasterDao.getAllData()

    suspend fun insert(tblTapTypeMaster: TblTapTypeMaster) {
        tblTapTypeMasterDao.insert(table = tblTapTypeMaster)
    }

    suspend fun deleteAll(tblTapTypeMaster: TblTapTypeMaster) {
        tblTapTypeMasterDao.deleteAll()
    }

    //for tap types and rates -- ends





    //for member types and members -- starts

    private var tblBoardMemberType: TblBoardMemberTypeDao = database.tblBoardMemberTypeDao()
    private var tblContact: TblContactDao = database.tblContactDao()


    val tblContacts: Flow<List<TblContact>> =
        tblContact.getAllData()



     suspend fun getContactList(memberTypeId: Int): Flow<List<TblContact>> {
       return tblContact.getFilteredContacts(memberTypeId)
    }



    suspend fun insert(tblContacts: TblContact) {
        tblContact.insert(table = tblContacts)
    }

    suspend fun deleteAll(tblContacts: TblContact) {
        tblContact.deleteAll()
    }


    val tblBoardMemberTypes: Flow<List<TblBoardMemberType>> =
        tblBoardMemberType.getAllData()

    suspend fun insert(tblBoardMemberTypes: TblBoardMemberType) {
        tblBoardMemberType.insert(table = tblBoardMemberTypes)
    }

    suspend fun deleteAll(tblBoardMemberTypes: TblBoardMemberType) {
        tblBoardMemberType.deleteAll()
    }


    //for member types and members -- ends

    suspend fun deleteAll() {
        tblReadingSetupDtlDao.deleteAll()
        tblReadingSetupDao.deleteAll()
        tblTapTypeMasterDao.deleteAll()

    }
}