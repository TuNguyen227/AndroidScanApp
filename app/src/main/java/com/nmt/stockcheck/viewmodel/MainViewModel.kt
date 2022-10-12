package com.nmt.stockcheck.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.haroldadmin.cnradapter.NetworkResponse
import com.nmt.stockcheck.AppApplication
import com.nmt.stockcheck.R
import com.nmt.stockcheck.config.Config
import com.nmt.stockcheck.model.GeneralResponse
import com.nmt.stockcheck.model.OCRAuthRequest
import com.nmt.stockcheck.model.OCRAuthenResponse
import com.nmt.stockcheck.repository.BaseRepository
import com.nmt.stockcheck.view.ui.scan.ScanActivity
import kotlinx.coroutines.*


open class MainViewModel(val application: AppApplication, val repository: BaseRepository): AndroidViewModel(application) {

    val isLoading=MutableLiveData<Boolean>()
    protected var job:Job?=null
    val loadError = MutableLiveData<String?>()
    val OCRAuthenResponse=MutableLiveData<OCRAuthenResponse>()

    inline fun <reified T> processResponse(response: NetworkResponse<Any, Any>,
                                           serverErrorHandler: ServerErrorHandler?=null): T? {
        when (response) {
            is NetworkResponse.Success -> {
                return response.body as T
            }
            is NetworkResponse.ServerError -> {
                serverErrorHandler?.onServerError(response.body,response.code)
            }
            is NetworkResponse.NetworkError -> {
                onError(
                    application.getString(R.string.network_error)
                )
            }
            is NetworkResponse.UnknownError -> {
                Log.d("StockCheck","-----"+response.error.localizedMessage+"-----")
                onError("We're sorry. Something went wrong. Please Try again")
            }
        }
        return null
    }

    fun onError(message: String) {
        loadError.value = message
        isLoading.value = false
    }
    fun authenOCRService(ocrAuthRequest: OCRAuthRequest) {
        isLoading.value=true
        job= CoroutineScope(Dispatchers.IO).launch {
            val response=repository.authenOCR(ocrAuthRequest)
            withContext(Dispatchers.Main){
                isLoading.value=false
                OCRAuthenResponse.value=processResponse(response,object: ServerErrorHandler{
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

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
interface ServerErrorHandler{
    fun onServerError(any: Any?,responseCode:Int?=null)

}
interface ErrorHandler{
    fun onError()
}