package com.bit.httpd.handler;

import com.bit.httpd.common.*;

//因为Handler接口还有doPost方法，但是很多子类并没有实现，所以用HandlerAdapter这个适配器
//也就是将doGet和doPost方法先实现，状态设置为该方法没有实现，即如果子类没有实现其中一个方法而调用
//将会是501错误
public class HandlerAdapter implements Handler {
    @Override
    public void doGet(HttpRequst httpRequst, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.NOT_IMPLEMENTED);
        httpResponse.flush();
    }

    @Override
    public void doPost(HttpRequst httpRequst, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.NOT_IMPLEMENTED);
        httpResponse.flush();
    }
}