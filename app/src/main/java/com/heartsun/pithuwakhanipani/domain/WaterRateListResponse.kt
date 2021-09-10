package com.heartsun.pithuwakhanipani.domain


import com.heartsun.pithuwakhanipani.domain.dbmodel.*

data class WaterRateListResponse(

    val readingSetupDetails: List<TBLReadingSetupDtl>,
    val readingSetup: List<TBLReadingSetup>,
    val tapType: List<TblTapTypeMaster>,
    val status:String,
    val message:String
)

data class MembersListResponse(
    val tblContact: List<TblContact>?,
    val tblBoardMemberType: List<TblBoardMemberType>?,
    val status:String,
    val message:String
    )

data class NoticesListResponse(
    val tblNotice: List<TblNotice>,
    val status:String,
    val message:String
)

data class AboutOrgResponse(
    val tblAbout: TblAboutOrg,
    val status:String,
    val message:String
)

data class ContactsListResponse(
    val tblDepartmentContact:List<TblDepartmentContact>,
    val status:String,
    val message:String
)

data class UserDetailsResponse(
    val tblMember: List<TblMember>?,
    val status:String,
    val message:String
)