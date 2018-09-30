package com.guiguchat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Service;

/**
 * Created by zyfx_lml on 2018/9/29.
 */
@Service("wSServer")
public class WSServer {
    public  void start() throws Exception {
        EventLoopGroup mainGroup = new NioEventLoopGroup();
        EventLoopGroup subGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(mainGroup, subGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new WSServerInitialzer());

            ChannelFuture future = server.bind(8088).sync();
            future.channel().closeFuture().sync();
        }finally {
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
