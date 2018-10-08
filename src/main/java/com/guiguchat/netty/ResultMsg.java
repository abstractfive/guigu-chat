package com.guiguchat.netty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zyfx_lml on 2018/9/30.
 */
public class ResultMsg implements Serializable {
    private Object msg;//返回内容
    private String resultCode="";//返回类型
    private String sendId;//发送人id
    private String sendName;//发送人用户名
    public enum ResultCodeType{
        //1注册名字 2发送消息 3新来了一个狗 4:历史记录
        REG("1"),CHAT("2"),HUO("3"),HISTORY("4");
        String value;

        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        ResultCodeType(String i) {
            this.value = i;
        }

    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public ResultMsg(Object msg, String resultCode) {
        this.msg = msg;
        this.resultCode = resultCode;
    }

    public ResultMsg(Object msg, String resultCode, String sendId, String sendName) {
        this.msg = msg;
        this.resultCode = resultCode;
        this.sendId = sendId;
        this.sendName = sendName;
    }

    public static String toJsonString(Object msg, String resultCode){
        return JSON.toJSONString(new ResultMsg(msg, resultCode));
    }
    public static String toJsonString(String msg, String resultCode, String sendId, String sendName){
        return JSON.toJSONString(new ResultMsg(msg, resultCode,sendId,sendName));
    }
    public static String toJsonString(ResultMsg resultMsg){
        return JSON.toJSONString(new ResultMsg(resultMsg.getMsg(), resultMsg.getResultCode(),resultMsg.getSendId(),resultMsg.sendName));
    }
    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }
}
