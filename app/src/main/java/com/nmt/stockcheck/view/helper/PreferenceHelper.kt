package com.nmt.stockcheck.view.helper

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.nmt.stockcheck.AppApplication
import com.nmt.stockcheck.BuildConfig

class PreferenceHelper{
    companion object {

        private fun getDefault(): SharedPreferences {
            val packageName = AppApplication.instance
                .packageName
            return AppApplication.instance
                .getSharedPreferences(packageName, Context.MODE_PRIVATE)
        }

        fun setObject(key: String, value: Any) {
            val gson = Gson()
            getDefault().edit().putString(key, gson.toJson(value))
                    .apply()
        }

        fun getObject(key: String, classType: Class<*>): Any? {
            try {
                val result = getDefault().getString(key, "")
                val gson = Gson()
                return gson.fromJson(result, classType)
            }
            catch (exp:Exception){
                return null
            }
        }

        fun setAccessToken(accessToken: String) {
            getDefault().edit().putString("accessToken", accessToken).apply()
        }

        fun getAccessToken(): String? {
            return getDefault().getString("accessToken",null)
        }

        fun setBaseURL(baseURL:String){
            getDefault().edit().putString("baseURL",baseURL)
                    .apply()
        }
        fun getBaseURL():String{
            return getDefault().getString("baseURL",BuildConfig.SERVER_OCR_URL)?:BuildConfig.SERVER_OCR_URL
        }

    }



}