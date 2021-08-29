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
) {
}