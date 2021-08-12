package com.heartsun.pithuwakhanipani.data.repository.databaseReppo

import com.heartsun.pithuwakhanipani.data.dao.TBLReadingSetupDao
import com.heartsun.pithuwakhanipani.data.dao.TBLReadingSetupDtlDao
import com.heartsun.pithuwakhanipani.data.dao.TblTapTypeMasterDao
import com.heartsun.pithuwakhanipani.data.database.KanipaniDatabase
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetup
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblTapTypeMaster
import kotlinx.coroutines.flow.Flow

class DbRepository(database: KanipaniDatabase) {

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




    suspend fun deleteAll() {
        tblReadingSetupDtlDao.deleteAll()
        tblReadingSetupDao.deleteAll()
        tblTapTypeMasterDao.deleteAll()

    }
}