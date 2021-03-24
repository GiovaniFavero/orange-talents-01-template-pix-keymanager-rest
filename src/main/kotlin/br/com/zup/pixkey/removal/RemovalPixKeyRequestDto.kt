package br.com.zup.pixkey.removal

import br.com.zup.PixKeyRemovalRequest
import br.com.zup.shared.validators.ValidUUID
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
class RemovalPixKeyRequestDto(
    @field:NotBlank @field:ValidUUID val pixId: String
) {
    fun toGrpcModel(customerId: String): PixKeyRemovalRequest {
        return PixKeyRemovalRequest.newBuilder()
            .setPixId(pixId)
            .setCustomerId(customerId)
            .build()
    }
}
