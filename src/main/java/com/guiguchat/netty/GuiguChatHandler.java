package com.guiguchat.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 龟谷聊天消息handler
 * Created by zyfx_lml on 2018/9/29.
 */
public class GuiguChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //记录用户
    private static ChannelGroup user=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static Map<String,String> userNmaes=new HashMap<>();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        try {
            String content = msg.text();
            System.out.println("接受到数据：" + content);
            JSONObject jsonObject= JSON.parseObject(content);
            String flag=jsonObject.getString("flag");
            String body=jsonObject.getString("body");
            String id=ctx.channel().id().asLongText();
            switch (flag) {
                case "reg":
                    //注册返回
                    if(findUserName(body)) {
                        userNmaes.put(id, body+"复刻");
                    }else{
                        userNmaes.put(id, body);
                    }
                    //发送id给前端
                    ctx.channel().writeAndFlush(new TextWebSocketFrame(ResultMsg.toJsonString(id,ResultMsg.ResultCodeType.REG.getValue())));
                    break;
                case "chat":
                    if(body.indexOf("<")!=-1){
                        ctx.channel().writeAndFlush(new TextWebSocketFrame(
                                ResultMsg.toJsonString("嘻嘻",
                                        ResultMsg.ResultCodeType.CHAT.getValue(),
                                        id,
                                        userNmaes.get(id))));
                    }else {
                        //聊天
                        user.writeAndFlush(new TextWebSocketFrame(
                                ResultMsg.toJsonString(body,
                                        ResultMsg.ResultCodeType.CHAT.getValue(),
                                        id,
                                        userNmaes.get(id))));
                    }
                    break;
            }
        }catch (Exception ex){
            //异常
            ctx.writeAndFlush(new TextWebSocketFrame("别瞎搞nmsl"));
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //将用户添加到用户group里面
        user.add(ctx.channel());
        Channel incoming = ctx.channel();
        for (Channel channel : user) {
            channel.writeAndFlush(new TextWebSocketFrame(ResultMsg.toJsonString("嚯嚯嚯 龟谷人+1",ResultMsg.ResultCodeType.HUO.getValue())));
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //这行代码会自动执行
        //user.remove(ctx.channel());
        userNmaes.remove(ctx.channel().id().asLongText());
        System.out.println("客户端断开 用户id:"+ctx.channel().id().asLongText());
    }
    private static boolean findUserName(String userName){
        for(String key:userNmaes.keySet())
        {
            if(userNmaes.get(key).equals(userName)){
                return true;
            }
        }
        return false;
    }
}
