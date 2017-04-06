package com.flyhtml.payment.grpc.nettyclient;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

/**
 * Created by xiaowei on 17-3-22.
 */
public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public NettyClientInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pe = ch.pipeline();

        pe.addLast("log", new LoggingHandler(LogLevel.DEBUG));

        // Enable HTTPS if necessary.
        if (sslCtx != null) {
            pe.addLast(sslCtx.newHandler(ch.alloc()));
        }

        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
        pe.addLast(new HttpResponseDecoder());

        // 客户端发送的是httpRequest，所以要使用HttpRequestEncoder进行编码
        pe.addLast(new HttpRequestEncoder());

        // Remove the following line if you don't want automatic content decompression.
        pe.addLast(new HttpContentDecompressor());

        // HttpObjectAggregator会把多个消息转换为 一个单一的FullHttpRequest或是FullHttpResponse
        // p.addLast("aggregator", new HttpObjectAggregator(1048576));

        pe.addLast("handler", new NettyClientHandler());
    }
}
