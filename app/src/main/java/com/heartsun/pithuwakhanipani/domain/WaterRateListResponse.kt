package com.heartsun.pithuwakhanipani.domain


import com.google.gson.annotations.SerializedName
import com.heartsun.pithuwakhanipani.domain.dbmodel.*

data class WaterRateListResponse(

    val readingSetupDetails: List<TBLReadingSetupDtl>,
    val readingSetup: List<TBLReadingSetup>,
    val tapType: List<TblTapTypeMaster>,

)

data class MembersListResponse(
    val tblContact: List<TblContact>,
    val tblBoardMemberType: List<TblBoardMemberType>,
    )

data class NoticesListResponse(
    val tblNotice: List<TblNotice>
)