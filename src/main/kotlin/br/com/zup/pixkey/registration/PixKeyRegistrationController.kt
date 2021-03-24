package br.com.zup.pixkey.registration

import br.com.zup.PixKeyRegistrationGrpcServiceGrpc
import br.com.zup.shared.validators.ValidUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.validation.Valid

@Validated
@Controller("/api/pix-key/{customerId}/keys")
class PixKeyRegistrationController (
    private val pixKeyManagerClient: PixKeyRegistrationGrpcServiceGrpc.PixKeyRegistrationGrpcServiceBlockingStub) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Post
    fun register (@PathVariable @ValidUUID customerId: String, @Valid @Body request: NewPixKeyRequestDto) : HttpResponse<Any> {

        logger.info("Creating pix key")

        val grpcResponse = pixKeyManagerClient.registerKey(request.toGrpcModel(customerId))


        return HttpResponse.created(location(customerId, grpcResponse.pixId))
    }

    private fun location(customerId: String, pixId: String) = HttpResponse.uri("/api/pix-key/$customerId/keys/$pixId")

}