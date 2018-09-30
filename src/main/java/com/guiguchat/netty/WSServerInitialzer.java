package com.guiguchat.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Created by zyfx_lml on 2018/9/29.
 */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline=ch.pipeline();

        //websocket 基于 http协议 这里是http的编解码
        pipeline.addLast(new HttpServerCodec());

        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        //http message 聚合器
        pipeline.addLast(new HttpObjectAggregator(1024*64));


        //websocket 服务器处理协议 路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //自定义
        pipeline.addLast(new GuiguChatHandler());



    }
}
