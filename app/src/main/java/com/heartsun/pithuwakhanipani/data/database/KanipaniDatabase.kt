package com.heartsun.pithuwakhanipani.data.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.heartsun.pithuwakhanipani.data.dao.TBLReadingSetupDao
import com.heartsun.pithuwakhanipani.data.dao.TBLReadingSetupDtlDao
import com.heartsun.pithuwakhanipani.data.dao.TblTapTypeMasterDao
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetup
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblTapTypeMaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [TBLReadingSetupDtl::class,TBLReadingSetup::class,TblTapTypeMaster::class], version = 1)
abstract class KanipaniDatabase :RoomDatabase() {

    abstract fun tBLReadingSetupDtlDao(): TBLReadingSetupDtlDao
    abstract fun tBLReadingSetupDao(): TBLReadingSetupDao
    abstract fun tblTapTypeMasterDao(): TblTapTypeMasterDao


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

                    tBLReadingSetupDtlDao.deleteAll()
                    tBLReadingSetupDao.deleteAll()
                    tblTapTypeMasterDao.deleteAll()

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