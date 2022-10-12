package com.nmt.stockcheck.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.haroldadmin.cnradapter.NetworkResponse
import com.nmt.stockcheck.AppApplication
import com.nmt.stockcheck.model.*
import com.nmt.stockcheck.repository.BaseRepository
import com.nmt.stockcheck.view.ui.scan.ScanActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScanViewModel(application: AppApplication,repository: BaseRepository) : MainViewModel(application,repository) {
    lateinit var driverLicencesDataObject:OCRModel.OCRDriverLicencesResponse.DriverLicencesData
    val imgScanVinResponseObserver=MutableLiveData<OCRModel.OCRVinResponse>()
    val imgScanRegoResponseObserver=MutableLiveData<OCRModel.OCRRegoResponse>()
    val imgScanDriverLicenseObserver=MutableLiveData<OCRModel.OCRDriverLicencesResponse>()
    val imgScanBarCodeObserver=MutableLiveData<OCRModel.OCRRegoResponse>()
    val imgScanQRCodeObserver=MutableLiveData<OCRModel.OCRRegoResponse>()
    val imgScanCarObserver=MutableLiveData<OCRModel.OCRRegoResponse>()
    fun scanVin(ocrRequest: OCRRequest)
    {
        isLoading.value=true
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=repository.scanVin( ocrRequest)
            withContext(Dispatchers.Main){
                isLoading.value=false
                imgScanVinResponseObserver.value=processResponse(response,object: ServerErrorHandler{
                    override fun onServerError(any: Any?, responseCode: Int?) {
                        if(any is GeneralResponse)
                        {
                            any.message?.let { onError(it)}
                        }
                    }
                })
            }
        }
    }

    fun scanRego(ocrRequest: OCRRequest)
    {
        isLoading.value=true
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=repository.scanRego( ocrRequest)
            withContext(Dispatchers.Main){
                isLoading.value=false
                imgScanRegoResponseObserver.value=processResponse(response,object: ServerErrorHandler{
                    override fun onServerError(any: Any?, responseCode: Int?) {
                        if(any is GeneralResponse)
                        {
                            any.message?.let { onError(it)}
                        }
                    }
                })
            }
        }
    }

    fun scanDriverLicense(ocrRequest: OCRRequest)
    {
        isLoading.value=true
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=repository.scanDriverLicense( ocrRequest)
            withContext(Dispatchers.Main){
                isLoading.value=false
                imgScanDriverLicenseObserver.value=processResponse(response,object: ServerErrorHandler{
                    override fun onServerError(any: Any?, responseCode: Int?) {
                        if(any is GeneralResponse)
                        {
                            any.message?.let { onError(it)}
                        }
                    }
                })

            }
        }
    }

    fun scanBarCode(ocrRequest: OCRRequest)
    {
        isLoading.value=true
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=repository.scanDriverLicense( ocrRequest)
            withContext(Dispatchers.Main){
                isLoading.value=false
                imgScanDriverLicenseObserver.value=processResponse(response,object: ServerErrorHandler{
                    override fun onServerError(any: Any?, responseCode: Int?) {
                        if(any is GeneralResponse)
                        {
                            any.message?.let { onError(it)}
                        }
                    }
                })

            }
        }
    }

    fun scanQRCode(ocrRequest: OCRRequest)
    {
        isLoading.value=true
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=repository.scanDriverLicense( ocrRequest)
            withContext(Dispatchers.Main){
                isLoading.value=false
                imgScanDriverLicenseObserver.value=processResponse(response,object: ServerErrorHandler{
                    override fun onServerError(any: Any?, responseCode: Int?) {
                        if(any is GeneralResponse)
                        {
                            any.message?.let { onError(it)}
                        }
                    }
                })

            }
        }
    }

    fun scanCar(ocrRequest: OCRRequest)
    {
        isLoading.value=true
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=repository.scanCar( ocrRequest)
            withContext(Dispatchers.Main){
                isLoading.value=false
                imgScanCarObserver.value=processResponse(response,object: ServerErrorHandler{
                    override fun onServerError(any: Any?, responseCode: Int?) {
                        if(any is GeneralResponse)
                        {
                            any.message?.let { onError(it)}
                        }
                    }
                })

            }
        }
    }


}