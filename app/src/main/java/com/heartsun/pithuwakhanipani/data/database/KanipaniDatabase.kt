package com.heartsun.pithuwakhanipani.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.heartsun.pithuwakhanipani.data.dao.*
import com.heartsun.pithuwakhanipani.domain.dbmodel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TBLReadingSetupDtl::class,TBLReadingSetup::class,TblTapTypeMaster::class,TblContact::class,TblBoardMemberType::class,TblNotice::class,TblAboutOrg::class,TblDepartmentContact::class], version = 1)
abstract class KanipaniDatabase :RoomDatabase() {

    abstract fun tBLReadingSetupDtlDao(): TBLReadingSetupDtlDao
    abstract fun tBLReadingSetupDao(): TBLReadingSetupDao
    abstract fun tblTapTypeMasterDao(): TblTapTypeMasterDao

    abstract fun tblContactDao(): TblContactDao
    abstract fun tblBoardMemberTypeDao(): TblBoardMemberTypeDao

    abstract fun tblNoticeDao(): TblNoticeDao
    abstract fun tblAboutOrgDao(): TblAboutOrgDao
    abstract fun tblDepartmentContactDao(): TblDepartmentContactDao


    private class KanipaniDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Instance?.let { database ->
                scope.launch {
                    val tBLReadingSetupDtlDao = database.tBLReadingSetupDtlDao()
                    val tBLReadingSetupDao = database.tBLReadingSetupDao()
                    val tblTapTypeMasterDao = database.tblTapTypeMasterDao()
                    val tblContactDao= database.tblContactDao()
                    val tblBoardMemberTypeDao= database.tblBoardMemberTypeDao()
                    val tblNoticeDao= database.tblNoticeDao()
                    val tblAboutOrgDao= database.tblAboutOrgDao()
                    val tblDepartmentContactDao= database.tblDepartmentContactDao()


                    tBLReadingSetupDtlDao.deleteAll()
                    tBLReadingSetupDao.deleteAll()
                    tblTapTypeMasterDao.deleteAll()
                    tblContactDao.deleteAll()
                    tblBoardMemberTypeDao.deleteAll()
                    tblNoticeDao.deleteAll()
                    tblAboutOrgDao.deleteAll()
                    tblDepartmentContactDao.deleteAll()
                }
            }
        }
    }

    companion object {
        @Volatile
        private var Instance: KanipaniDatabase? = null

        fun getKanipaniDatabase(context: Context, scope: CoroutineScope): KanipaniDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KanipaniDatabase::class.java,
                    "khanipani_database"
                ).addCallback(KanipaniDatabaseCallback(scope = scope)).build()
                Instance = instance
                instance
            }
        }
    }
}