package com.nmt.stockcheck.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.haroldadmin.cnradapter.NetworkResponse
import com.nmt.stockcheck.api.AppApi
import com.nmt.stockcheck.model.*
import com.nmt.stockcheck.view.helper.PreferenceHelper
import retrofit2.http.Body
import retrofit2.http.POST

class BaseRepository(private val appApi:AppApi){

    suspend fun authenOCR(ocrAuthRequest: OCRAuthRequest) : NetworkResponse<OCRAuthenResponse,GeneralResponse>{
        return appApi.getOCR(ocrAuthRequest)
    }

    suspend fun scanVin(ocrRequest: OCRRequest) : NetworkResponse<OCRModel.OCRVinResponse,GeneralResponse>{
        return appApi.getVin(token=buildAccessTokenHeader(),ocrRequest)
    }

    suspend fun scanRego(ocrRequest: OCRRequest) : NetworkResponse<OCRModel.OCRRegoResponse,GeneralResponse>{
        return appApi.getRego(token = buildAccessTokenHeader(),ocrRequest)
    }

    suspend fun scanDriverLicense(ocrRequest: OCRRequest) : NetworkResponse<OCRModel.OCRDriverLicencesResponse,GeneralResponse>{
        return  appApi.getDriverLicense(token = buildAccessTokenHeader(),ocrRequest)
    }

    suspend fun scanCar(ocrRequest: OCRRequest) : NetworkResponse<OCRModel.OCRRegoResponse,GeneralResponse>{
        return appApi.getCarDetail(token = buildAccessTokenHeader(),ocrRequest)
    }

    private fun buildAccessTokenHeader(): String {
        return "Bearer "+ PreferenceHelper.getAccessToken()
    }
}