package com.heartsun.pithuwakhanipani.domain


import com.google.gson.annotations.SerializedName
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetup
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblTapTypeMaster

data class WaterRateListResponse(

    val readingSetupDetails: List<TBLReadingSetupDtl>,
    val readingSetup: List<TBLReadingSetup>,
    val tapType: List<TblTapTypeMaster>,

)