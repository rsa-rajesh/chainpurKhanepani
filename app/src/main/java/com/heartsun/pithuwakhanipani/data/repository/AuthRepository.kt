package com.heartsun.pithuwakhanipani.data.repository

import android.content.Context
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.data.apis.AuthApi
import com.heartsun.pithuwakhanipani.domain.MembersListResponse
import com.heartsun.pithuwakhanipani.domain.WaterRateListResponse
import com.heartsun.pithuwakhanipani.domain.dbmodel.TBLReadingSetupDtl
import kotlinx.coroutines.CoroutineDispatcher

interface AuthRepository {
    suspend fun getRates(status: String): WaterRateListResponse
   suspend fun getMembers(): MembersListResponse
//    suspend fun login(request: LoginRequest): UiState<LoginResponse>
//    suspend fun register(request: RegisterRequest): UiState<RegisterResponse>
//    suspend fun validateOTP(request: ValidateOTPRequest): UiState<ValidateOTPResponse>

}

class AuthRepoImpl(
    private val authApi: AuthApi,
    private val connection: ConnectionToServer,
    private val prefs: Prefs,
    private val dispatcher: CoroutineDispatcher,
    private val context: Context
) : AuthRepository {
    override suspend fun getRates(status: String): WaterRateListResponse {
        return connection.getRates()
    }

    override suspend fun getMembers(): MembersListResponse {
        return connection.getMembers()
    }


}