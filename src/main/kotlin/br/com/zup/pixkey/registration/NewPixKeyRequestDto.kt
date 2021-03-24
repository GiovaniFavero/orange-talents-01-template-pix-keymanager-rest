package br.com.zup.pixkey.registration

import br.com.zup.AccountType
import br.com.zup.KeyType
import br.com.zup.PixKeyRequest
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ValidPixKey
data class NewPixKeyRequestDto(
    @field:NotNull val keyType: PixKeyType?,
    @field:Size(max = 77) val key: String?,
    @field:NotNull val accountType: AccountType?
) {

    fun toGrpcModel(customerId: String): PixKeyRequest {
        return PixKeyRequest.newBuilder()
            .setCustomerId(customerId)
            .setKeyType(KeyType.valueOf(this.keyType?.name ?: KeyType.UNKNOWN_KEY_TYPE.name))
            .setKey(this.key ?: "")
            .setAccountType(this.accountType ?: AccountType.UNKNOWN_ACCOUNT)
            .build()
    }

}
