package com.emt.base.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by dsj on 2017/3/17.
 * 定义统一返回对象格式
 */
public class ReturnObject {

    public enum SuccessEnum {
        success(0),
        fail(1);
        private int successEnum ;
        private SuccessEnum(int successEnum) {
            this.successEnum=successEnum;
        }
        public int getSuccessEnum () {
            return successEnum;
        }

    }

    public int retcode ;//成功 0；失败 非0
    public String retmsg ;
    public Object data;
    public int count;
    public Object messages;


    /**
     * @return the object
     */
    public Object getData() {
        return data;
    }

    /**
     * @param object
     *            the object to set
     */
    public void setData(Object data) {
        this.data = data;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count
     *            the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    public Object getMessages()
    {
        return messages;
    }

    public void setMessages(Object messages)
    {
        this.messages = messages;
    }

    public ReturnObject(SuccessEnum success, String message, Object object, int count) {
        super();
        this.retcode = success.getSuccessEnum();
        this.retmsg = message;
        this.data = object;
        this.count = count;
    }

    public ReturnObject(SuccessEnum success, Object messages, Object object, int count) {
        super();
        this.retcode = success.getSuccessEnum();
        this.messages = messages;
        this.data = object;
        this.count = count;
    }

    public ReturnObject() {
        super();
        this.retcode = 0;
        this.retmsg = "成功";
        this.data = null;
        this.count = 0;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }


    public void fail(String msg){
        this.retcode = 1;
        this.retmsg = msg;
        this.data = null;
        this.count = 0;
    }
    public String toJson(){
        String strJson = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            strJson = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return strJson;
    }
}
