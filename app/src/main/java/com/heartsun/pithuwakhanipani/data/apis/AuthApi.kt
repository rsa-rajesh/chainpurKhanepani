package com.heartsun.pithuwakhanipani.data.apis

import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.di.DataSourceProperties.BASE_PATH
import com.heartsun.pithuwakhanipani.domain.apiResponse.ServerDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface AuthApi {
    companion object {

        private const val userId = Prefs.UserID
        private const val GetServerDetails = "$BASE_PATH/getcredentials.php"

    }

    @GET(GetServerDetails)
    suspend fun getServerDetails(@Path("MobAppID") appID: String): Response<ServerDetailsResponse>

}