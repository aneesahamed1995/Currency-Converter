package com.demo.converter.data.network.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BankDetailResponse(
    @Json(name = "valid") val valid: Boolean? = null,
    @Json(name = "iban") val iban: String? = null,
    @Json(name = "iban_data") val data: BankDataEntity? = null,
    @Json(name = "bank_data") val bankData: BankEntity? = null

) : BaseResponse() {
    @JsonClass(generateAdapter = true)
    data class BankDataEntity(
        @Json(name = "country") val country: String? = null,
        @Json(name = "bank_code") val bankCode: String? = null,
        @Json(name = "account_number") val accountNo: String? = null,
        @Json(name = "checksum") val checksum: String? = null
    )

    @JsonClass(generateAdapter = true)
    data class BankEntity(
        @Json(name = "name") val name: String? = null,
        @Json(name = "zip") val zip: String? = null,
        @Json(name = "city") val city: String? = null,
        @Json(name = "bic") val bic: String? = null
    )
}