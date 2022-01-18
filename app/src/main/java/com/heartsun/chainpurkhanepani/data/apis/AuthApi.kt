package com.heartsun.chainpurkhanepani.data.apis

import com.heartsun.chainpurkhanepani.data.Prefs
import com.heartsun.chainpurkhanepani.di.DataSourceProperties.BASE_PATH
import com.heartsun.chainpurkhanepani.domain.apiResponse.ServerDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface AuthApi {
    companion object {
        private const val userId = Prefs.UserID
        private const val GetServerDetails = "$BASE_PATH/getcredentials.php"
    }
    @GET(GetServerDetails)
    suspend fun getServerDetails(@Query("MobAppID") appID: String): Response<ServerDetailsResponse>
}