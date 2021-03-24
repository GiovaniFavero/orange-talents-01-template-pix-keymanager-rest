package br.com.zup.shared.grpc

import br.com.zup.PixKeyConsultationServiceGrpc
import br.com.zup.PixKeyListConsultationServiceGrpc
import br.com.zup.PixKeyRegistrationGrpcServiceGrpc
import br.com.zup.PixKeyRemovalGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class PixKeyManagerGrpcFactory (@GrpcChannel("keyManager") val channel: ManagedChannel){

    @Singleton
    fun registerKey() = PixKeyRegistrationGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun removeKey() = PixKeyRemovalGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun getPixKeyDetail() = PixKeyConsultationServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun getPixKeyList() = PixKeyListConsultationServiceGrpc.newBlockingStub(channel)

}