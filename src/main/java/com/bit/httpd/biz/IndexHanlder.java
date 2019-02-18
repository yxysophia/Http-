package com.bit.httpd.biz;

import com.bit.httpd.common.Handler;
import com.bit.httpd.common.HttpRequst;
import com.bit.httpd.common.HttpResponse;
import com.bit.httpd.handler.HandlerAdapter;


//url: /
public class IndexHanlder extends HandlerAdapter {
    @Override
    public void doGet(HttpRequst httpRequst, HttpResponse httpResponse) {
        httpResponse.setContentType("text/html; charset=UTF-8"); //响应内容类型
        httpResponse.write("<h1> Hello Web Http </h1>");
        httpResponse.flush();
    }
}
