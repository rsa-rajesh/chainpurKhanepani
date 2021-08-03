package com.heartsun.pithuwakhanipani.data.apis

import com.heartsun.pithuwakhanipani.data.Prefs


interface AuthApi {
    companion object {


        private const val userId = Prefs.UserID

//        private const val LoginApi = "$BASE_PATH/staff-get-token/"
//        private const val ValidateOTPApi = "$BASE_PATH/logisticstaff/otp-validation/"
//        private const val ResendOTPApi = "$BASE_PATH/logisticstaff/otp-validation/"
//        private const val RiderRegisterApi = "$BASE_PATH/logisticstaff/register/"
//        private const val SendOtpToEmail = "$BASE_PATH/logisticstaff-forgot-password/user-query/"
//
//        private const val resetPassword = "$BASE_PATH/logisticstaff/new-password-confirm/"
//        private const val PostEditProfile = "$BASE_PATH/logisticstaff/profile/$userId/update"

    }

//    @POST(LoginApi)
//    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
//
//    @Multipart
//    @POST(RiderRegisterApi)
//    suspend fun register(
//        @Part("email") email: RequestBody,
//        @Part("password") password: RequestBody,
//        @Part("name") name: RequestBody,
//        @Part("mobile") mobile: RequestBody,
//        @Part("gender") gender: RequestBody,
//        @Part("address") address: RequestBody,
//        @Part("age") age: RequestBody,
//        @Part("vehicle_number") vehicleNumber: RequestBody,
//        @Part("citizenship_number") citizenshipNumber: RequestBody,
//        @Part("bluebook_number") bluebookNumber: RequestBody,
//        @Part("license_number") licenseNumber: RequestBody,
//        @Part citizenshipDocument: MultipartBody.Part,
//        @Part licenseDocument: MultipartBody.Part,
//        @Part bluebookDocument: MultipartBody.Part,
//    ): Response<RegisterResponse>
//
//
//    @POST(SendOtpToEmail)
//    suspend fun sendOtpToEmail(@Body request: SendOtpRequest): Response<CommonResponse>
//
//    @POST(ValidateOTPApi)
//    suspend fun validateOtp(@Body request: ValidateOTPRequest): Response<ValidateOTPResponse>
//
//    @POST(ResendOTPApi)
//    suspend fun resendOtp(@Body request: ResendOTPRequest): Response<ResendOTPResponse>
//
//    @POST(resetPassword)
//    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<CommonResponse>
//
//    @POST(PostEditProfile)
//    suspend fun postEditProfile(@Body request: EditProfileRequest): Response<EditProfileResponse>

}