package io.grpc.payment.callback;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 * <pre>
 * The greeting service definition.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.3)",
    comments = "Source: callback.proto")
public class CallBackGrpc {

  private CallBackGrpc() {}

  public static final String SERVICE_NAME = "callback.CallBack";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<io.grpc.payment.callback.CallBackParam,
      io.grpc.payment.callback.ReturnParam> METHOD_ALI_PAY_NOTIFY =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "callback.CallBack", "aliPayNotify"),
          io.grpc.protobuf.ProtoUtils.marshaller(io.grpc.payment.callback.CallBackParam.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(io.grpc.payment.callback.ReturnParam.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CallBackStub newStub(io.grpc.Channel channel) {
    return new CallBackStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CallBackBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new CallBackBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static CallBackFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new CallBackFutureStub(channel);
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static abstract class CallBackImplBase implements io.grpc.BindableService {

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void aliPayNotify(io.grpc.payment.callback.CallBackParam request,
        io.grpc.stub.StreamObserver<io.grpc.payment.callback.ReturnParam> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_ALI_PAY_NOTIFY, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_ALI_PAY_NOTIFY,
            asyncUnaryCall(
              new MethodHandlers<
                io.grpc.payment.callback.CallBackParam,
                io.grpc.payment.callback.ReturnParam>(
                  this, METHODID_ALI_PAY_NOTIFY)))
          .build();
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class CallBackStub extends io.grpc.stub.AbstractStub<CallBackStub> {
    private CallBackStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CallBackStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CallBackStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CallBackStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public void aliPayNotify(io.grpc.payment.callback.CallBackParam request,
        io.grpc.stub.StreamObserver<io.grpc.payment.callback.ReturnParam> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_ALI_PAY_NOTIFY, getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class CallBackBlockingStub extends io.grpc.stub.AbstractStub<CallBackBlockingStub> {
    private CallBackBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CallBackBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CallBackBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CallBackBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public io.grpc.payment.callback.ReturnParam aliPayNotify(io.grpc.payment.callback.CallBackParam request) {
      return blockingUnaryCall(
          getChannel(), METHOD_ALI_PAY_NOTIFY, getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * The greeting service definition.
   * </pre>
   */
  public static final class CallBackFutureStub extends io.grpc.stub.AbstractStub<CallBackFutureStub> {
    private CallBackFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private CallBackFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CallBackFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new CallBackFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Sends a greeting
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<io.grpc.payment.callback.ReturnParam> aliPayNotify(
        io.grpc.payment.callback.CallBackParam request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_ALI_PAY_NOTIFY, getCallOptions()), request);
    }
  }

  private static final int METHODID_ALI_PAY_NOTIFY = 0;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final CallBackImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(CallBackImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ALI_PAY_NOTIFY:
          serviceImpl.aliPayNotify((io.grpc.payment.callback.CallBackParam) request,
              (io.grpc.stub.StreamObserver<io.grpc.payment.callback.ReturnParam>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_ALI_PAY_NOTIFY);
  }

}
