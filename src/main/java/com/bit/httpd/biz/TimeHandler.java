package com.bit.httpd.biz;

import com.bit.httpd.common.Handler;
import com.bit.httpd.common.HttpRequst;
import com.bit.httpd.common.HttpResponse;
import com.bit.httpd.handler.HandlerAdapter;

import java.time.LocalDateTime;

public class TimeHandler extends HandlerAdapter {
    @Override
    public void doGet(HttpRequst httpRequst, HttpResponse httpResponse) {
        httpResponse.setContentType("text/html;charset=UTF-8");
        httpResponse.write("当前时间为:");
        httpResponse.write(LocalDateTime.now().toString());
    }
}
