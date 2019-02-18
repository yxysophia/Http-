package com.bit.httpd.handler;

import com.bit.httpd.common.Handler;
import com.bit.httpd.common.HttpRequst;
import com.bit.httpd.common.HttpResponse;
import com.bit.httpd.common.SupportMimeType;

//关于信息
public class AboutHanlder extends HandlerAdapter {
    @Override
    public void doGet(HttpRequst httpRequst, HttpResponse httpResponse) {
        httpResponse.setContentType(SupportMimeType.HTM.getMimeType());
        httpResponse.write("<h2>名称：Java-httpd</h2>");
        httpResponse.write("<h2>描述：基于Java的web服务器，支持静态和动态内容</h2>");
        httpResponse.write("<h2>作者：sophia </h2>");
        httpResponse.write("<h2>版本：Java-httpd Version 1.0</h2>");
        httpResponse.flush();
    }
}
