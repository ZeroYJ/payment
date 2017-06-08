package com.flyhtml.payment.grpc.interceptor;

import java.time.Instant;

import com.flyhtml.payment.common.exception.PaymentException;
import io.grpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.grpc.ServerCall.Listener;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author xiaowei
 * @time 17-4-25 上午10:54
 * @describe grpc异常捕获类
 */
public class ExceptionInterceptor implements ServerInterceptor {

  private static final Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class);

  @Override
  public <ReqT, RespT> Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
    logger.info("GRPC call at: {}", Instant.now());
    ServerCall.Listener<ReqT> reqTListener = next.startCall(call, headers);
    // ServerCall.Listener<ReqT> listener;
    //
    // try {
    // listener = ;
    // } catch (Throwable ex) {
    // logger.error("Uncaught exception from grpc service");
    // call.close(Status.INTERNAL.withCause(ex).withDescription("Uncaught exception from grpc service"), null);
    // return new ServerCall.Listener<ReqT>() {
    // };
    // }
    return new ExceptionListener(reqTListener, call);
  }
}

class ExceptionListener extends Listener {

  private Listener delegate;
  private ServerCall call;

  public ExceptionListener(Listener reqTListener, ServerCall call) {
    this.delegate = reqTListener;
    this.call = call;
  }

  @Override
  public void onHalfClose() {
    try {
      this.delegate.onHalfClose();
    } catch (StatusRuntimeException t) {
      // 统一处理异常
      // 调用 call.close() 发送 Status 和 metadata
      // 这个方式和 onError()本质是一样的
      call.close(t.getStatus(), t.getTrailers());
    }
  }
}
