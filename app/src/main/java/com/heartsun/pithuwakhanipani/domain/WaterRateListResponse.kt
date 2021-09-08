package com.heartsun.pithuwakhanipani.domain


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

data class AboutOrgResponse(
    val tblAbout: TblAboutOrg
)

data class ContactsListResponse(
    val tblDepartmentContact:List<TblDepartmentContact>,
)

data class UserDetailsResponse(
    val tblMember: List<TblMember>?,
    val status:String,
    val message:String
)