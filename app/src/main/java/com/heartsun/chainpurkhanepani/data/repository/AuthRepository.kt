package com.heartsun.chainpurkhanepani.data.repository

import android.content.Context
import androidcommon.handler.doTryCatch
import androidcommon.handler.handleResponse
import androidcommon.utils.UiState
import com.heartsun.chainpurkhanepani.data.apis.AuthApi
import com.heartsun.chainpurkhanepani.domain.*
import com.heartsun.chainpurkhanepani.domain.apiResponse.ServerDetailsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface AuthRepository {
    suspend fun getRates(status: String): WaterRateListResponse
    suspend fun getMembers(): MembersListResponse
    suspend fun getNotices(): NoticesListResponse?
    suspend fun getAboutOrg(): AboutOrgResponse?
    suspend fun getContacts(): ContactsListResponse?
    suspend  fun getFilesRequirement(): DocumentTypesResponse?
    fun sendRegistrationRequest(details: RegistrationRequest?, context: Context): String?
    suspend fun getBillDetails(memberId: Int): BillDetailsResponse?
    suspend fun getUserDetails(phoneNo: String, pin: String): UserDetailsResponse?
    suspend fun requestPin(phoneNo: String, memberId: String): String?
    suspend fun changePinCode(phoneNo: String?, memberId: String?, newPin: String): String?
    suspend fun addComplaint(message: String, memberID: String?, phoneNo: String?): String
    suspend fun getComplaintList(memberID: String?, phoneNo: String?): MutableList<ComplaintResponse>?
    suspend fun getActivities(): ActivitiesListResponse?
    suspend fun getSliderImages(): SliderListResponse?
    suspend fun getServerDetailsFromServer(appID: String): UiState<ServerDetailsResponse>?
    suspend fun getLedgerDetails(memberId: Int): LedgerDetailsResponse?
}

class AuthRepoImpl(
    private val authApi: AuthApi,
    private val connection: ConnectionToServer,
    private val dispatcher: CoroutineDispatcher,
    private val context: Context
) : AuthRepository {
    override suspend fun getRates(status: String): WaterRateListResponse {
        return connection.getRates()
    }

    override suspend fun getMembers(): MembersListResponse {
        return connection.getMembers()
    }

    override suspend fun getNotices(): NoticesListResponse {
        return connection.getNotices(context)
    }

    override suspend fun getAboutOrg(): AboutOrgResponse {
        return connection.getAboutOrg()
    }

    override suspend fun getContacts(): ContactsListResponse {
        return connection.getContactList()
    }

    override suspend fun getFilesRequirement(): DocumentTypesResponse {
        return connection.getRequiredFiles()
    }

    override fun sendRegistrationRequest(details: RegistrationRequest?, context: Context): String {
        return connection.requestForReg(details,context)
    }

    override suspend fun getBillDetails(memberId:Int): BillDetailsResponse {
        return connection.getBillDetails(memberId)
    }

    override suspend fun getUserDetails(phoneNo: String, pin: String): UserDetailsResponse {
        return connection.addTapResponse(phoneNo, pin)
    }

    override suspend fun requestPin(phoneNo: String, memberId: String): String {
        return connection.requestPin(phoneNo,memberId)
    }

    override suspend fun changePinCode(
        phoneNo: String?,
        memberId: String?,
        newPin: String
    ): String? {
        return connection.changePin(phoneNo,memberId,newPin)
    }

    override suspend fun addComplaint(message: String, memberID: String?, phoneNo: String?): String {
        return connection.addComplaint(message,memberID,phoneNo)
    }

    override suspend fun getComplaintList(
        memberID: String?,
        phoneNo: String?
    ): MutableList<ComplaintResponse>? {
        return connection.getComplaintList(memberID,phoneNo)
    }



    override suspend fun getActivities(): ActivitiesListResponse {
        return connection.getActivities(context)
    }

    override suspend fun getSliderImages(): SliderListResponse {
        return connection.getSliderImages(context)
    }

    override suspend fun getServerDetailsFromServer(appID: String): UiState<ServerDetailsResponse> {
        return withContext(dispatcher){
            doTryCatch {
                authApi.getServerDetails(appID=appID).handleResponse()
            }
        }
    }

    override suspend fun getLedgerDetails(memberId: Int): LedgerDetailsResponse {
        return connection.getLedgerDetails(memberId)
    }
}