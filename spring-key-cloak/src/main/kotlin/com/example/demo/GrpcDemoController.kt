package com.example.demo

import io.grpc.examples.helloworld.HelloReply
import io.grpc.examples.helloworld.HelloRequest
import io.grpc.examples.helloworld.GreeterGrpc
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.springframework.security.access.annotation.Secured

@GRpcService
@Secured()
class GreeterService() : GreeterGrpc.GreeterImplBase() {

    override fun sayHello(request: HelloRequest, responseObserver: StreamObserver<HelloReply>) {
        val reply = HelloReply.newBuilder()
            .setMessage("Hello " + request.getName())
            .build()
        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }
}
