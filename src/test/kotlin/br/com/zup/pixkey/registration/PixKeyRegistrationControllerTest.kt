package br.com.zup.pixkey.registration

import br.com.zup.AccountType
import br.com.zup.PixKeyRegistrationGrpcServiceGrpc
import br.com.zup.PixKeyResponse
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
internal class PixKeyRegistrationControllerTest {

    @field:Inject
    lateinit var registerStub: PixKeyRegistrationGrpcServiceGrpc.PixKeyRegistrationGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `Must register a Pix Key` () {
        val customerId = UUID.randomUUID().toString()
        val pixKeyId = UUID.randomUUID().toString()

        val grpcResponse = PixKeyResponse
            .newBuilder()
            .setPixId(pixKeyId)
            .build()

        BDDMockito.given(registerStub.registerKey(Mockito.any())).willReturn(grpcResponse)

        val newPixKey = NewPixKeyRequestDto(
            keyType = PixKeyType.CPF,
            key = "09030442930",
            accountType = AccountType.CURRENT_ACCOUNT)

        val request = HttpRequest.POST("/api/pix-key/$customerId/keys", newPixKey)
        val response = client.toBlocking().exchange(request, NewPixKeyRequestDto::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains("Location"))
        assertTrue(response.header("Location").contains(pixKeyId))
    }

    @Factory
    @Replaces(factory = PixKeyManagerGrpcFactory::class)
    internal class MockitoStubFactory {
        @Singleton
        fun stubMock() = Mockito.mock(PixKeyRegistrationGrpcServiceGrpc.PixKeyRegistrationGrpcServiceBlockingStub::class.java)
    }

}