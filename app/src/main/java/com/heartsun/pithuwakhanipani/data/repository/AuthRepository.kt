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
    suspend fun getContacts(): ContactsListResponse?
    suspend  fun getFilesRequirement(): DocumentTypesResponse?
    suspend fun sendRegistrationRequest(details: RegistrationRequest?, context: Context): String?
    suspend fun getBillDetails(memberId: Int): BillDetailsResponse?
    suspend fun getUserDetails(phoneNo: String, pin: String): UserDetailsResponse?
    suspend fun requestPin(phoneNo: String, memberId: String): String?
    suspend fun changePinCode(phoneNo: String?, memberId: String?, newPin: String): String?
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

    override suspend fun getContacts(): ContactsListResponse? {
        return connection.getContactList(context)
    }

    override suspend fun getFilesRequirement(): DocumentTypesResponse? {
        return connection.getRequiredFiles(context)
    }

    override suspend fun sendRegistrationRequest(details: RegistrationRequest?, context: Context): String? {
        return connection.requestForReg(details,context)
    }

    override suspend fun getBillDetails(memberId:Int): BillDetailsResponse? {
        return connection.getBillDetails(context,memberId)
    }

    override suspend fun getUserDetails(phoneNo: String, pin: String): UserDetailsResponse? {
        return connection.addTapResponce(context,phoneNo,pin)
    }

    override suspend fun requestPin(phoneNo: String, memberId: String): String? {
        return connection.requestPin(phoneNo,memberId)
    }

    override suspend fun changePinCode(
        phoneNo: String?,
        memberId: String?,
        newPin: String
    ): String? {
        return connection.changePin(phoneNo,memberId,newPin)
    }
}