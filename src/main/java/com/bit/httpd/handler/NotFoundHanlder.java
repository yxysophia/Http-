package com.bit.httpd.handler;

import com.bit.httpd.common.Handler;
import com.bit.httpd.common.HttpRequst;
import com.bit.httpd.common.HttpResponse;
import com.bit.httpd.common.HttpStatus;

public class NotFoundHanlder extends HandlerAdapter {
    @Override
    public void doGet(HttpRequst httpRequst, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.NOT_FOUND);  //如果是404会自动有404的页面
        httpResponse.flush();

    }

}
