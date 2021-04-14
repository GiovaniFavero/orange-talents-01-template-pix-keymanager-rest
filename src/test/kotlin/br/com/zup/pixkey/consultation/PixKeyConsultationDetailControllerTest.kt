package br.com.zup.pixkey.consultation

import br.com.zup.*
import br.com.zup.pixkey.removal.RemovalPixKeyRequestDto
import br.com.zup.shared.grpc.PixKeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class PixKeyConsultationDetailControllerTest {

    @field:Inject
    lateinit var registerStub: PixKeyConsultationServiceGrpc.PixKeyConsultationServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `Must get pix key details`() {
        val customerId = UUID.randomUUID().toString()
        val pixKeyId = UUID.randomUUID().toString()

        val grpcResponse = PixKeyConsultationResponse
            .newBuilder()
            .setPixId(pixKeyId)
            .setCustomerId(customerId)
            .setKey(PixKeyConsultationResponse.PixKeyDetails
                .newBuilder()
                .setKeyType(KeyType.EMAIL)
                .setKey("email@email.com")
                .setAccount(PixKeyConsultationResponse.PixKeyDetails.Account.newBuilder()
                    .setAccountType(AccountType.CURRENT_ACCOUNT)
                    .setInstitution("institution")
                    .setOwnerName("ownerName")
                    .setOwnerCpf("ownerCpf")
                    .setBranch("branch")
                    .setAccountNumber("accountNumber")
                    .build())
                .build()
                )
            .build()

        BDDMockito.given(registerStub.getPixKeyDetail(Mockito.any())).willReturn(grpcResponse)

        val request = HttpRequest.GET<Any>("/api/$customerId/keys/$pixKeyId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
    }

    @Factory
    @Replaces(factory = PixKeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(PixKeyConsultationServiceGrpc.PixKeyConsultationServiceBlockingStub::class.java)
    }

}