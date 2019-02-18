package com.bit.httpd.common;

//服务器针对浏览器的请求所做出的处理
public interface Handler {

    //浏览器请求方法是Get
    void doGet(HttpRequst httpRequst, HttpResponse httpResponse);

    //浏览器请求方法是POST
    void doPost(HttpRequst httpRequst, HttpResponse httpResponse);
}
