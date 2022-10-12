package com.nmt.stockcheck.api

import com.haroldadmin.cnradapter.NetworkResponse
import com.nmt.stockcheck.BuildConfig
import com.nmt.stockcheck.model.*
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AppApi {
    @POST("Vins")
    suspend fun getVin(@Header("Authorization") token:String, @Body  ocrRequest: OCRRequest ) : NetworkResponse<OCRModel.OCRVinResponse,GeneralResponse>

    @POST("Regos")
    suspend fun getRego(@Header("Authorization") token:String, @Body  ocrRequest: OCRRequest ) : NetworkResponse<OCRModel.OCRRegoResponse,GeneralResponse>

    @POST("DriverLicences")
    suspend fun getDriverLicense(@Header("Authorization") token:String, @Body  ocrRequest: OCRRequest ) : NetworkResponse<OCRModel.OCRDriverLicencesResponse,GeneralResponse>

    @POST("GlassGuide/CarDetail")
    suspend fun getCarDetail(@Header("Authorization") token: String, @Body ocrRequest: OCRRequest) : NetworkResponse<OCRModel.OCRRegoResponse,GeneralResponse>

    @POST("${BuildConfig.SERVER_OCR_AUTH_URL}Authorise")
    suspend fun getOCR(@Body ocrAuthRequest: OCRAuthRequest) : NetworkResponse<OCRAuthenResponse,GeneralResponse>
}