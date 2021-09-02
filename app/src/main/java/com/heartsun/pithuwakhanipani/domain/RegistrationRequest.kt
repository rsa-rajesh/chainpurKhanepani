package com.heartsun.pithuwakhanipani.domain


import android.net.Uri
import com.google.gson.annotations.SerializedName
import com.heartsun.pithuwakhanipani.domain.dbmodel.TblDepartmentContact

data class RegistrationRequest(

    @SerializedName("memName")
    var MemName: String?,

    @SerializedName("address")
    var Address: String?,

    @SerializedName("gender")
    var Gender: String?,

    @SerializedName("citNo")
    var CitNo: String?,

    @SerializedName("contactNo")
    var ContactNo: String?,

    @SerializedName("fHName")
    var FHName: String?,

    @SerializedName("gFILName")
    var GFILName: String?,

    @SerializedName("maleCount")
    var MaleCount: String?,

    @SerializedName("femaleCount")
    var FemaleCount: String?,


    @SerializedName("files")
    var files: List<RequiredDocuments>?
) {
    data class RequiredDocuments(
        @SerializedName("documentName")
        var DocumentName: String?,

        @SerializedName("docImage")
        var DocImage: String?
    )
}

data class DocumentTypesResponse(
    val documentTypes: List<RegistrationRequest.RequiredDocuments>,
)

data class BillDetails(
    @SerializedName("MemberID")
    var MemberID: Int?,
    @SerializedName("MemName")
    var MemName: String?,
    @SerializedName("TapNo")
    var TapNo: Int?,
    @SerializedName("Address")
    var Address: String?,
    @SerializedName("TapType")
    var TapType: String?,
    @SerializedName("RID")
    var RID: Int?,
    @SerializedName("TotReading")
    var TotReading: Int?,
    @SerializedName("Amt")
    var Amt: Float?,
    @SerializedName("Inv_Date")
    var Inv_Date: String?,
    @SerializedName("Sam_Date")
    var Sam_Date: String?,
    @SerializedName("Dis")
    var Dis: Float?,
    @SerializedName("Fine")
    var Fine: Float?,
    @SerializedName("NetAmt")
    var NetAmt: Float?
)

data class BillDetailsResponse(
    val billDetails: List<BillDetails>
)