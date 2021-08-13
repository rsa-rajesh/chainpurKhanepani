package com.heartsun.pithuwakhanipani.domain.dbmodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tBLReadingSetupDtl",primaryKeys = ["VNO","SrNo"])
data class TBLReadingSetupDtl (
    @ColumnInfo(name = "VNO")
    val VNO: Int,
    @ColumnInfo(name = "SrNo")
    val SrNo: Int,
    @ColumnInfo(name = "MnNo")
    val MnNo: Int?,
    @ColumnInfo(name = "MxNo")
    val MxNo: Int?,
    @ColumnInfo(name = "Amt")
    val Amt: Float?,
    @ColumnInfo(name = "Rate")
    val Rate: Float?,
)

@Entity(tableName = "tBLReadingSetup")
data class TBLReadingSetup (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "VNO")
    val VNO: Int,
    @ColumnInfo(name = "FixCharges")
    val FixCharges: Int?,
    @ColumnInfo(name = "TapTypeID")
    val TapTypeID: Int?,
    @ColumnInfo(name = "TapSizeID")
    val TapSizeID: Int?,
    @ColumnInfo(name = "Remarks")
    val Remarks: String?,
)

@Entity(tableName = "tblTapTypeMaster")
data class TblTapTypeMaster (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "TapTypeID")
    val TapTypeID: Int,
    @ColumnInfo(name = "TapTypeName")
    val TapTypeName: String,
)


@Entity(tableName = "tblContact")
data class TblContact (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ContID")
    val ContID: Int,
    @ColumnInfo(name = "ContactName")
    val ContactName: String?,
    @ColumnInfo(name = "ContactNumber")
    val ContactNumber: String?,
    @ColumnInfo(name = "IsActive")
    val IsActive: Int,
    @ColumnInfo(name = "Post")
    val Post: String?,
    @ColumnInfo(name = "MemberType")
    val MemberType: Int,
    @ColumnInfo(name = "Tenure")
    val Tenure: String?,
)


@Entity(tableName = "tblBoardMemberType")
data class TblBoardMemberType (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "MemTypeID")
    val MemTypeID: Int,
    @ColumnInfo(name = "MemberType")
    val MemberType: String?,
)