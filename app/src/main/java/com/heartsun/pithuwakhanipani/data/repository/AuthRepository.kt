package com.heartsun.pithuwakhanipani.data.repository

import android.content.Context
import com.heartsun.pithuwakhanipani.data.Prefs
import com.heartsun.pithuwakhanipani.data.apis.AuthApi
import kotlinx.coroutines.CoroutineDispatcher

interface AuthRepository {
//    suspend fun login(request: LoginRequest): UiState<LoginResponse>
//    suspend fun register(request: RegisterRequest): UiState<RegisterResponse>
//    suspend fun validateOTP(request: ValidateOTPRequest): UiState<ValidateOTPResponse>

}

class AuthRepoImpl(
    private val authApi: AuthApi,
    private val prefs: Prefs,
    private val dispatcher: CoroutineDispatcher,
    private val context: Context
) : AuthRepository {
//    override suspend fun login(request: LoginRequest): UiState<LoginResponse> {
//        return withContext(dispatcher) {
//            doTryCatch {
//                authApi.login(request).handleResponse {
//                    prefs.isLoggedIn = true
//                    prefs.authToken = it.token
//                    prefs.userId = it.user_id
//                }
//            }
//        }
//    }
//
//    override suspend fun register(request: RegisterRequest): UiState<RegisterResponse> {
//        return withContext(dispatcher) {
//            doTryCatch {
//                authApi.register(
//                    email = request.email.orEmpty().toRequestBody(),
//                    password = request.password.orEmpty().toRequestBody(),
//                    name = request.name.orEmpty().toRequestBody(),
//                    mobile = request.mobile.orEmpty().toRequestBody(),
//                    gender = request.gender.orEmpty().toRequestBody(),
//                    address = request.address.orEmpty().toRequestBody(),
//                    age = request.age.toString().toRequestBody(),
//                    vehicleNumber = request.vehicle_number.orEmpty().toRequestBody(),
//                    citizenshipNumber = request.citizenship_number.orEmpty().toRequestBody(),
//                    bluebookNumber = request.bluebook_number.orEmpty().toRequestBody(),
//                    licenseNumber = request.license_number.orEmpty().toRequestBody(),
//                    bluebookDocument = context.getFile(request.bluebook_document.toString())
//                        .toMultiPart("bluebook_document"),
//                    citizenshipDocument = context.getFile(request.citizenship_document.toString())
//                        .toMultiPart("citizenship_document"),
//                    licenseDocument = context.getFile(request.license_document.toString())
//                        .toMultiPart("license_document"),
//                ).handleResponse()
//
//            }
//        }
//    }
//
//    override suspend fun validateOTP(request: ValidateOTPRequest): UiState<ValidateOTPResponse> {
//        return withContext(dispatcher) {
//            doTryCatch {
//                authApi.validateOtp(request).handleResponse()
//            }
//        }
//    }
//

}