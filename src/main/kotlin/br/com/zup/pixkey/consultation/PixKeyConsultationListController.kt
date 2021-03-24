package br.com.zup.pixkey.consultation

import br.com.zup.PixKeyListConsultationServiceGrpc
import br.com.zup.PixKeyListRequest
import br.com.zup.shared.validators.ValidUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory

@Validated
@Controller("/api/{customerId}/keys")
class PixKeyConsultationListController(
    private val grpcClient: PixKeyListConsultationServiceGrpc.PixKeyListConsultationServiceBlockingStub
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Get
    fun listAll(@PathVariable @ValidUUID customerId: String) : HttpResponse<Any> {

        logger.info("Getting key list of customer id $customerId")

        val response = grpcClient.getPixKeyList(PixKeyListRequest.newBuilder()
            .setCustomerId(customerId)
            .build())

        return HttpResponse.ok(PixKeyListResponseDto(response))
    }

}