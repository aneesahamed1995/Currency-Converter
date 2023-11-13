package com.kfh.converter.data.mapper

import com.kfh.converter.common.extension.getOrDefault
import com.kfh.converter.data.network.response.BankDetailResponse
import com.kfh.converter.domain.entity.Bank

class BankMapper {

    fun mapFrom(entity:BankDetailResponse) = Bank(
        entity.iban.getOrDefault(),
        entity.data?.country.getOrDefault(),
        entity.data?.checksum.getOrDefault(),
        entity.data?.bankCode.getOrDefault(),
        entity.data?.accountNo.getOrDefault(),
        entity.bankData?.name.getOrDefault(),
        entity.bankData?.zip.getOrDefault(),
        entity.bankData?.city.getOrDefault(),
        entity.bankData?.bic.getOrDefault()
    )


}