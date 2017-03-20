package com.flyhtml.payment;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.payment.callback.CallBackGrpc;
import io.grpc.payment.callback.CallBackParam;
import io.grpc.payment.callback.ReturnParam;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by xiaowei on 17-2-20.
 */
public class CallBackServer {
    private static final Logger logger = Logger.getLogger(CallBackServer.class.getName());

    /* The port on which the server should run */
    private int port = 50051;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new CallBackService())
                .build()
                .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                CallBackServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final CallBackServer server = new CallBackServer();
        server.start();
        server.blockUntilShutdown();
    }

    private class CallBackService extends CallBackGrpc.CallBackImplBase {

        @Override
        public void aliPayNotify(CallBackParam request, StreamObserver<ReturnParam> responseObserver) {
            super.aliPayNotify(request, responseObserver);
        }
    }
}
