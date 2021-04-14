package br.com.zup.pixkey.consultation

import br.com.zup.*
import br.com.zup.shared.grpc.PixKeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class PixKeyConsultationListControllerTest {

    @field:Inject
    lateinit var registerStub: PixKeyListConsultationServiceGrpc.PixKeyListConsultationServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `Must get pix key list`() {
        val customerId = UUID.randomUUID().toString()
        val pixKeyId = UUID.randomUUID().toString()

        val grpcResponse = PixKeyListResponse
            .newBuilder()
            .setCustomerId(customerId)
            .addAllKeys(listOf(PixKeyListResponse.PixKey.newBuilder()
                .setPixId("pixId")
                .setType(KeyType.EMAIL)
                .setKey("email@email.com")
                .setAccountType(AccountType.CURRENT_ACCOUNT)
                .build()))
            .build()

        BDDMockito.given(registerStub.getPixKeyList(Mockito.any())).willReturn(grpcResponse)

        val request = HttpRequest.GET<Any>("/api/$customerId/keys")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
    }

    @Factory
    @Replaces(factory = PixKeyManagerGrpcFactory::class)
    internal class MockitoStubFactoryGrpc {
        @Singleton
        fun stubMock() = Mockito.mock(PixKeyListConsultationServiceGrpc.PixKeyListConsultationServiceBlockingStub::class.java)
    }
}