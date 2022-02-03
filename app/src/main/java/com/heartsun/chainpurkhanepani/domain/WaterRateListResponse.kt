package com.heartsun.chainpurkhanepani.domain


import com.heartsun.chainpurkhanepani.domain.dbmodel.*

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

data class ActivitiesListResponse(
    val tblActivity: List<TblActivity>,
    val status:String,
    val message:String
)

data class SliderListResponse(
    val tblSliderImages: List<TblSliderImages>,
    val status:String,
    val message:String
)



data class AboutOrgResponse(
    val tblAbout: TblAboutOrg?,
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