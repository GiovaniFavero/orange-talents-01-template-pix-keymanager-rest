package br.com.zup.pixkey.removal

import br.com.zup.PixKeyRemovalGrpcServiceGrpc
import br.com.zup.PixKeyRemovalResponse
import br.com.zup.shared.grpc.PixKeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.Mockito
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class PixKeyRemovalControllerTest {

    @field:Inject
    lateinit var registerStub: PixKeyRemovalGrpcServiceGrpc.PixKeyRemovalGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `Must remove a pix key`() {
        val customerId = UUID.randomUUID().toString()
        val pixKeyId = UUID.randomUUID().toString()

        val grpcResponse = PixKeyRemovalResponse
            .newBuilder()
            .setMessage("Pix key removed successfully!")
            .build()

        BDDMockito.given(registerStub.removeKey(Mockito.any())).willReturn(grpcResponse)

        val removalRequest = RemovalPixKeyRequestDto(pixId = pixKeyId)

        val request = HttpRequest.DELETE("/api/pix-key/$customerId/keys", removalRequest)
        val response = client.toBlocking().exchange(request, RemovalPixKeyRequestDto::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
    }

    @Factory
    @Replaces(factory = PixKeyManagerGrpcFactory::class)
    internal class MockitoStubFactoryGrpc {
        @Singleton
        fun stubMock() = Mockito.mock(PixKeyRemovalGrpcServiceGrpc.PixKeyRemovalGrpcServiceBlockingStub ::class.java)
    }
}