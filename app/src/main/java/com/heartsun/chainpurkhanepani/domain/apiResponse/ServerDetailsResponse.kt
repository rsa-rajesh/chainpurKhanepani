package com.heartsun.chainpurkhanepani.domain.apiResponse

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ServerDetailsResponse(

    @SerializedName("response_code")
    @Expose
    val responseCode: String,

    @SerializedName("response_message")
    @Expose
    val responseMessage: String,

    @SerializedName("database_name")
    @Expose
    val databaseName: String?,

    @SerializedName("db_server_name")
    @Expose
    val dbServerName: String?,

    @SerializedName("db_user_name")
    @Expose
    val dbUserName: String?,

    @SerializedName("db_password")
    @Expose
    val dbPassword: String?
)
