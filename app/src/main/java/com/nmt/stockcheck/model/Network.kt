package com.nmt.stockcheck.model

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName



class GeneralResponse(
    @SerializedName("statusCode")
    var statusCode: Int? = null,
    @SerializedName("message")
    var message: String? = null
)

data class GeneralValidationResponse(
    @SerializedName("statusCode")
    var statusCode: Int? = null,
    @SerializedName("message")
    var message: JsonElement? = null
){
    data class Message(
        @SerializedName("target")
        var target: String? = null,
        @SerializedName("message")
        var message: String? = null
    )
}

