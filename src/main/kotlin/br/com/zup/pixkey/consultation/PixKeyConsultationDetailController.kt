package br.com.zup.pixkey.consultation

import br.com.zup.PixKeyConsultationRequest
import br.com.zup.PixKeyConsultationServiceGrpc
import br.com.zup.shared.validators.ValidUUID
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory

@Validated
@Controller("/api/{customerId}/keys")
class PixKeyConsultationDetailController (
    private val consultationClient: PixKeyConsultationServiceGrpc.PixKeyConsultationServiceBlockingStub
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Get("/{pixId}")
    fun getPixKeyDetail(
        @PathVariable @ValidUUID customerId: String,
        @PathVariable @ValidUUID pixId: String
    ) : HttpResponse<Any> {

        logger.info("Getting pix key by Id $pixId")

        val response = PixKeyConsultationRequest.newBuilder()
            .setPixId(PixKeyConsultationRequest.PixKeyId.newBuilder()
                .setCustomerId(customerId)
                .setPixId(pixId)
                .build())
            .build()
            .let {
                consultationClient.getPixKeyDetail(it)
            }

        return HttpResponse.ok(PixKeyDetailResponseDto(response))
    }

}