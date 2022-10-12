package com.nmt.stockcheck.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class OCRModel(
    @SerializedName("dob")
    var dob: String?="",
    @SerializedName("driverLicenceExpiryDate")
    var driverLicenceExpiryDate: String?="",
    @SerializedName("driverLicenceNumber")
    var driverLicenceNumber: String?="",
    @SerializedName("firstName")
    var firstName: String?="",
    @SerializedName("lastName")
    var lastName: String?="",
    @SerializedName("parsedContent")
    var parsedContent: String?,
    @SerializedName("postCode")
    var postCode: String?="",
    @SerializedName("state")
    var state: String?="",
    @SerializedName("streetLine1")
    var streetLine1: String?="",
    @SerializedName("suburb")
    var suburb: String?="",
    @SerializedName("vin")
    var vin: String?,
    @SerializedName("rego")
    var rego: String?,
    @SerializedName("version")
    var version: String?
) :  Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(dob)
        parcel.writeString(driverLicenceExpiryDate)
        parcel.writeString(driverLicenceNumber)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(parsedContent)
        parcel.writeString(postCode)
        parcel.writeString(state)
        parcel.writeString(streetLine1)
        parcel.writeString(suburb)
        parcel.writeString(vin)
        parcel.writeString(rego)
        parcel.writeString(version)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OCRModel> {
        override fun createFromParcel(parcel: Parcel): OCRModel {
            return OCRModel(parcel)
        }

        override fun newArray(size: Int): Array<OCRModel?> {
            return arrayOfNulls(size)
        }
    }
    data class OCRDriverLicencesResponse(
        @SerializedName("code")
        var code: Int? = null,
        @SerializedName("data")
        var `data`: DriverLicencesData? = null,
        @SerializedName("message")
        var message: String? = null,
        @SerializedName("receiptId")
        var receiptId: String? = null
    ) {

        data class DriverLicencesData(
            @SerializedName("countryCode")
            var countryCode: String? = null,
            @SerializedName("dob")
            var dob: String? = null,
            @SerializedName("driverLicenceExpiryDate")
            var driverLicenceExpiryDate: String? = null,
            @SerializedName("driverLicenceNumber")
            var driverLicenceNumber: String? = null,
            @SerializedName("firstName")
            var firstName: String? = null,
            @SerializedName("lastName")
            var lastName: String? = null,
            @SerializedName("parsedContent")
            var parsedContent: String? = null,
            @SerializedName("postCode")
            var postCode: String? = null,
            @SerializedName("state")
            var state: String? = null,
            @SerializedName("streetLine1")
            var streetLine1: String? = null,
            @SerializedName("suburb")
            var suburb: String? = null,
            @SerializedName("version")
            var version: Any? = null
        ) : Parcelable {
            constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
            )


            override fun describeContents(): Int {
                return 0
            }

            override fun writeToParcel(dest: Parcel?, flags: Int) {
                dest?.writeString(countryCode)
                dest?.writeString(dob)
                dest?.writeString(driverLicenceExpiryDate)
                dest?.writeString(driverLicenceNumber)
                dest?.writeString(firstName)
                dest?.writeString(lastName)
                dest?.writeString(parsedContent)
                dest?.writeString(postCode)
                dest?.writeString(state)
                dest?.writeString(streetLine1)
                dest?.writeString(suburb)
                dest?.writeString(version as String?)
            }

            companion object CREATOR : Parcelable.Creator<DriverLicencesData> {
                override fun createFromParcel(parcel: Parcel): DriverLicencesData {
                    return DriverLicencesData(parcel)
                }

                override fun newArray(size: Int): Array<DriverLicencesData?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
    data class OCRVinResponse(
        @SerializedName("code")
        var code: Int?=null,
        @SerializedName("data")
        var `data`: VinData?=null,
        @SerializedName("message")
        var message: String?=null,
        @SerializedName("receiptId")
        var receiptId: String?=null
    ){
        data class VinData(
            @SerializedName("parsedContent")
            var parsedContent: String?=null,
            @SerializedName("vin")
            var vin: String?=null
            )
    }
    data class OCRRegoResponse(
        @SerializedName("code")
        var code: Int? = null,
        @SerializedName("data")
        var `data`: RegoData? = null,
        @SerializedName("message")
        var message: String? = null,
        @SerializedName("receiptId")
        var receiptId: String? = null
    ) {
        data class RegoData(
            @SerializedName("parsedContent")
            var parsedContent: String? = null,
            @SerializedName("rawParsedContent")
            var rawParsedContent: List<RawParsedContent?>? = null,
            @SerializedName("rego")
            var rego: String? = null,
            @SerializedName("state")
            var state: String? = null
        ) {

            data class RawParsedContent(
                @SerializedName("blocks")
                var blocks: List<Block?>? = null,
                @SerializedName("confidence")
                var confidence: Double? = null,
                @SerializedName("height")
                var height: Int? = null,
                @SerializedName("property")
                var `property`: Property? = null,
                @SerializedName("width")
                var width: Int? = null
            ) {

                data class Block(
                    @SerializedName("blockType")
                    var blockType: Int? = null,
                    @SerializedName("boundingBox")
                    var boundingBox: BoundingBox? = null,
                    @SerializedName("confidence")
                    var confidence: Double? = null,
                    @SerializedName("paragraphs")
                    var paragraphs: List<Paragraph?>? = null,
                    @SerializedName("property")
                    var `property`: Any? = null
                ) {

                    data class BoundingBox(
                        @SerializedName("normalizedVertices")
                        var normalizedVertices: List<Any?>? = null,
                        @SerializedName("vertices")
                        var vertices: List<Vertice?>? = null
                    ) {

                        data class Vertice(
                            @SerializedName("x")
                            var x: Int? = null,
                            @SerializedName("y")
                            var y: Int? = null
                        )
                    }


                    data class Paragraph(
                        @SerializedName("boundingBox")
                        var boundingBox: BoundingBox? = null,
                        @SerializedName("confidence")
                        var confidence: Double? = null,
                        @SerializedName("property")
                        var `property`: Any? = null,
                        @SerializedName("words")
                        var words: List<Word?>? = null
                    ) {

                        data class BoundingBox(
                            @SerializedName("normalizedVertices")
                            var normalizedVertices: List<Any?>? = null,
                            @SerializedName("vertices")
                            var vertices: List<Vertice?>? = null
                        ) {

                            data class Vertice(
                                @SerializedName("x")
                                var x: Int? = null,
                                @SerializedName("y")
                                var y: Int? = null
                            )
                        }


                        data class Word(
                            @SerializedName("boundingBox")
                            var boundingBox: BoundingBox? = null,
                            @SerializedName("confidence")
                            var confidence: Double? = null,
                            @SerializedName("property")
                            var `property`: Property? = null,
                            @SerializedName("symbols")
                            var symbols: List<Symbol?>? = null
                        ) {

                            data class BoundingBox(
                                @SerializedName("normalizedVertices")
                                var normalizedVertices: List<Any?>? = null,
                                @SerializedName("vertices")
                                var vertices: List<Vertice?>? = null
                            ) {

                                data class Vertice(
                                    @SerializedName("x")
                                    var x: Int? = null,
                                    @SerializedName("y")
                                    var y: Int? = null
                                )
                            }


                            data class Property(
                                @SerializedName("detectedBreak")
                                var detectedBreak: Any? = null,
                                @SerializedName("detectedLanguages")
                                var detectedLanguages: List<DetectedLanguage?>? = null
                            ) {

                                data class DetectedLanguage(
                                    @SerializedName("confidence")
                                    var confidence: Double? = null,
                                    @SerializedName("languageCode")
                                    var languageCode: String? = null
                                )
                            }


                            data class Symbol(
                                @SerializedName("boundingBox")
                                var boundingBox: BoundingBox? = null,
                                @SerializedName("confidence")
                                var confidence: Double? = null,
                                @SerializedName("property")
                                var `property`: Property? = null,
                                @SerializedName("text")
                                var text: String? = null
                            ) {

                                data class BoundingBox(
                                    @SerializedName("normalizedVertices")
                                    var normalizedVertices: List<Any?>? = null,
                                    @SerializedName("vertices")
                                    var vertices: List<Vertice?>? = null
                                ) {

                                    data class Vertice(
                                        @SerializedName("x")
                                        var x: Int? = null,
                                        @SerializedName("y")
                                        var y: Int? = null
                                    )
                                }


                                data class Property(
                                    @SerializedName("detectedBreak")
                                    var detectedBreak: DetectedBreak? = null,
                                    @SerializedName("detectedLanguages")
                                    var detectedLanguages: List<Any?>? = null
                                ) {

                                    data class DetectedBreak(
                                        @SerializedName("isPrefix")
                                        var isPrefix: Boolean? = null,
                                        @SerializedName("type")
                                        var type: Int? = null
                                    )
                                }
                            }
                        }
                    }
                }


                data class Property(
                    @SerializedName("detectedBreak")
                    var detectedBreak: Any? = null,
                    @SerializedName("detectedLanguages")
                    var detectedLanguages: List<DetectedLanguage?>? = null
                ) {

                    data class DetectedLanguage(
                        @SerializedName("confidence")
                        var confidence: Double? = null,
                        @SerializedName("languageCode")
                        var languageCode: String? = null
                    )
                }
            }
        }
    }
}

data class OCRRequest(val countryCode: String, val imageData:String)
data class OCRAuthRequest(val systemCode: String, val customer:String, val reference:String, val apiKey:String)
data class OCRAuthenResponse(val data: JsonElement, val code:Int, val message:String)

