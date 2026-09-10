package com.simats.com.network.response

import com.simats.com.network.request.SignInRequest
import com.simats.com.network.request.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/signup/")
    suspend fun signUp(@Body request: SignUpRequest): Response<Any>

    @POST("api/signin/")
    suspend fun signIn(@Body request: SignInRequest): Response<Any>

    @POST("api/generate_id/")
    suspend fun generateId(@Body request: com.simats.com.network.request.GetNotificationsRequest): Response<com.simats.com.network.response.GenerateIdResponse>

    @POST("api/pair_device/")
    suspend fun pairDevice(@Body request: com.simats.com.network.request.PairDeviceRequest): Response<Any>

    @POST("api/upload_notification/")
    suspend fun uploadNotification(@Body request: com.simats.com.network.request.NotificationUploadRequest): Response<Any>

    @POST("api/get_notifications/")
    suspend fun getNotifications(@Body request: com.simats.com.network.request.GetNotificationsRequest): Response<com.simats.com.network.response.NotificationResponse>

    @POST("api/get_family_members/")
    suspend fun getFamilyMembers(@Body request: com.simats.com.network.request.GetFamilyMembersRequest): Response<com.simats.com.network.response.FamilyMembersResponse>

    @POST("api/delete_device/")
    suspend fun deleteDevice(@Body request: com.simats.com.network.request.DeleteDeviceRequest): Response<Any>

    @POST("api/get_paired_admin/")
    suspend fun getPairedAdmin(@Body request: com.simats.com.network.request.GetAdminRequest): Response<com.simats.com.network.response.AdminResponse>

    @POST("api/update_device_meta/")
    suspend fun updateDeviceMeta(@Body request: com.simats.com.network.request.UpdateDeviceMetaRequest): Response<Any>

    @POST("api/get_user_alerts/")
    suspend fun getUserAlerts(@Body request: com.simats.com.network.request.GetNotificationsRequest): Response<com.simats.com.network.response.NotificationResponse>

    @POST("api/clear_data/")
    suspend fun clearData(@Body request: com.simats.com.network.request.ClearDataRequest): Response<com.simats.com.network.response.ClearDataResponse>
}
