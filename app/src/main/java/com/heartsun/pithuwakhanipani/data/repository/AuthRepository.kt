package com.heartsun.pithuwakhanipani.data.repository

import android.content.Context
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.data.apis.AuthApi
import com.heartsun.pithuwakhanipani.domain.*
import kotlinx.coroutines.CoroutineDispatcher

interface AuthRepository {
    suspend fun getRates(status: String): WaterRateListResponse
    suspend fun getMembers(): MembersListResponse
    suspend fun getNotices(): NoticesListResponse?
    suspend fun getAboutOrg(): AboutOrgResponse?
    fun getContacts(): ContactsListResponse?
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
        return connection.getMembers(context)
    }

    override suspend fun getNotices(): NoticesListResponse? {
        return connection.getNotices(context)
    }

    override suspend fun getAboutOrg(): AboutOrgResponse? {
        return connection.getAboutOrg(context)
    }

    override fun getContacts(): ContactsListResponse? {
        return connection.getContactList(context)

    }
}