package br.com.zup.shared

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@MicronautTest
internal class GlobalExceptionHandlerTest {

    val genericRequest = HttpRequest.GET<Any>("/")

    @Test
    fun `Must return 404 when grpc StatusRuntimeException for not found` () {
        this.handleGrpcException(StatusRuntimeException(Status.NOT_FOUND), HttpStatus.NOT_FOUND)
    }

    @Test
    fun `Must return 400 when grpc StatusRuntimeException for invalid argument` () {
        this.handleGrpcException(StatusRuntimeException(Status.INVALID_ARGUMENT), HttpStatus.BAD_REQUEST)
    }

    @Test
    fun `Must return 422 when grpc StatusRuntimeException for already exists` () {
        this.handleGrpcException(StatusRuntimeException(Status.ALREADY_EXISTS), HttpStatus.UNPROCESSABLE_ENTITY)
    }

    @Test
    fun `Must return 500 when grpc StatusRuntimeException for any` () {
        this.handleGrpcException(StatusRuntimeException(Status.ABORTED), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun handleGrpcException (grpcException: StatusRuntimeException, httpStatus: HttpStatus) {
        val response = GlobalExceptionHandler().handle(genericRequest, grpcException)

        assertEquals(httpStatus, response.status)
        assertNotNull(response.body())
    }
}