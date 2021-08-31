package com.heartsun.pithuwakhanipani.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.heartsun.pithuwakhanipani.domain.dbmodel.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TblNoticeDao {
    @Query("SELECT * FROM tblNotice")
    fun getAllData(): Flow<List<TblNotice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(table: TblNotice)

    @Query("DELETE FROM tblNotice")
    suspend fun deleteAll()
}

@Dao
interface TblAboutOrgDao {
    @Query("SELECT * FROM tblAboutOrg")
    fun getAllData(): Flow<List<TblAboutOrg>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(table: TblAboutOrg)

    @Query("DELETE FROM tblAboutOrg")
    suspend fun deleteAll()
}

@Dao
interface TblDepartmentContactDao {
    @Query("SELECT * FROM tblDepartmentContact")
    fun getAllData(): Flow<List<TblDepartmentContact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(table: TblDepartmentContact)

    @Query("DELETE FROM tblDepartmentContact")
    suspend fun deleteAll()
}

@Dao
interface TblDocumentTypeDao {
    @Query("SELECT * FROM tblDocumentType")
    fun getAllData(): Flow<List<TblDocumentType>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(table: TblDocumentType)

    @Query("DELETE FROM tblDocumentType")
    suspend fun deleteAll()
}