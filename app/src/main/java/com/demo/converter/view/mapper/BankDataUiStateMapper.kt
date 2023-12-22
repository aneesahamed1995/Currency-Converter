package com.demo.converter.view.mapper

import com.demo.converter.domain.entity.Bank
import com.demo.converter.view.model.BankUiState

class BankDataUiStateMapper {
    fun mapFrom(bank: Bank) = BankUiState(
        bank.iban,
        bank.country,
        bank.checksum,
        bank.bankCode,
        bank.accountNumber,
        bank.name,
        bank.zip,
        bank.city,
        bank.bic
    )
}