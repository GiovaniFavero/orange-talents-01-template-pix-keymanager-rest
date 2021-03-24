package br.com.zup.pixkey.removal

import br.com.zup.PixKeyRemovalGrpcServiceGrpc
import br.com.zup.shared.validators.ValidUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/pix-key/{customerId}/keys")
class PixKeyRemovalController (
    @Inject private val pixKeyManagerClient: PixKeyRemovalGrpcServiceGrpc.PixKeyRemovalGrpcServiceBlockingStub
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Delete
    fun remove(@PathVariable @ValidUUID customerId: String, @Valid @Body request: RemovalPixKeyRequestDto) : HttpResponse<Any> {
        logger.info("Deleting pix key with Id ${request.pixId}")

        val grpcResponse = pixKeyManagerClient.removeKey(request.toGrpcModel(customerId))

        return HttpResponse.ok()
    }
}