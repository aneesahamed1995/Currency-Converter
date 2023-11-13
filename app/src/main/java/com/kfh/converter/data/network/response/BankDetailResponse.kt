package com.kfh.converter.data.network.response

import com.google.gson.annotations.SerializedName

data class BankDetailResponse(
    @SerializedName("valid") val valid: Boolean? = null,
    @SerializedName("iban") val iban: String? = null,
    @SerializedName("iban_data") val data: BankDataEntity? = null,
    @SerializedName("bank_data") val bankData: BankEntity? = null

) : BaseResponse() {
    data class BankDataEntity(
        @SerializedName("country") val country: String? = null,
        @SerializedName("bank_code") val bankCode: String? = null,
        @SerializedName("account_number") val accountNo: String? = null,
        @SerializedName("checksum") val checksum: String? = null
    )

    data class BankEntity(
        @SerializedName("name") val name: String? = null,
        @SerializedName("zip") val zip: String? = null,
        @SerializedName("city") val city: String? = null,
        @SerializedName("bic") val bic: String? = null)
}