package com.nmt.stockcheck.config


import com.nmt.stockcheck.BuildConfig

class Config{

        companion object  {

            val CLOUD_VISION_API_KEY= "AIzaSyD6d3sgKi3TKw9nQ0nMe_5YmCAQtZsR8VU"
            val CLOUD_VISION_DETECT_TYPE= "DOCUMENT_TEXT_DETECTION"
            val CUSTOMER_TYPE="Customer"
            val MAX_CACHE_DIR_SIZE = 20*1000*1000L


            var DATE_TIME_PATTERN="dd/MM/yyyy HH:mm"
            var LIMIT=5000
            var MAP_ZOOM=18F
            //var MAP_DEFAULT_TYPE= GoogleMap.MAP_TYPE_TERRAIN


            var AUTHEN_KEY="Basic c3RvY2tjaGVjazpuc2Jtd3NpbGljb24="
            val SERVER_DATE_FORMAT : String = "yyyy-MM-dd'T'HH:mm:ssZ"
            val COMBINE_DATE_TIME_FORMAT : String = "dd/MM/yyyy HH:mm"
                        val UI_DATE_FORMAT : String = "dd/MM/yyyy"
            val UI_TIME_FORMAT : String = "h:mm a"
            val REQUEST_OPEN_CAMERA_DIRECTLY = 100
            var BASE_URL="http://192.168.1.163/ocr/"
            val OCR_COUNTRY_CODE="vi-vn"
            val OCR_SYSTEM_CODE="SSD"
            val OCR_API_KEY="8315677f30cf43b5981759713b1c1273"

            const val ANIMATION_DURATION=500L
            const val NETWORK_TIMEOUT :Long = 60*1000L
            const val DEBUG_TAG="---Feedjoy---"
            const val DEVICE_TYPE="Android"
            const val PAGING_LIMIT=10
            const val PAGING_UNLIMIT=100000

        }

}


