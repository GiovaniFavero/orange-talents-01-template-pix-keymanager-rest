package br.com.zup.pixkey.consultation

import br.com.zup.AccountType
import br.com.zup.PixKeyConsultationResponse
import io.micronaut.core.annotation.Introspected

@Introspected
class PixKeyDetailResponseDto (response: PixKeyConsultationResponse) {

    val pixId = response.pixId
    val keyType = response.key.keyType
    val key = response.key.key

    val accountType = when (response.key.account.accountType) {
        AccountType.CURRENT_ACCOUNT -> "CURRENT_ACCOUNT"
        AccountType.SAVINGS_ACCOUNT -> "SAVINGS_ACCOUNT"
        else -> "UNKNOWN_ACCOUNT"
    }

    val account = mapOf(
        Pair("type", accountType),
        Pair("institution", response.key.account.institution),
        Pair("ownerName", response.key.account.ownerName),
        Pair("ownerCpf", response.key.account.ownerCpf),
        Pair("branch", response.key.account.branch),
        Pair("number", response.key.account.accountNumber),
    )

    override fun toString(): String {
        return "PixKeyDetailResponseDto(pixId='$pixId', keyType=$keyType, key='$key', accountType='$accountType', account=$account)"
    }

}