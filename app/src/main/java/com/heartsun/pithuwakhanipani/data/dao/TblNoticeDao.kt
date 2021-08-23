package com.heartsun.pithuwakhanipani.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetup
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblNotice
import kotlinx.coroutines.flow.Flow

@Dao
interface  TblNoticeDao {
    @Query("SELECT * FROM tblNotice")
    fun getAllData(): Flow<List<TblNotice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(table: TblNotice)

    @Query("DELETE FROM tblNotice")
    suspend fun deleteAll()
}