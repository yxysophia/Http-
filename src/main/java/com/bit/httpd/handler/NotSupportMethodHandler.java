package com.bit.httpd.handler;

import com.bit.httpd.common.Handler;
import com.bit.httpd.common.HttpRequst;
import com.bit.httpd.common.HttpResponse;
import com.bit.httpd.common.HttpStatus;

public class NotSupportMethodHandler extends HandlerAdapter {
    @Override
    public void doGet(HttpRequst httpRequst, HttpResponse httpResponse) {
        httpResponse.setHttpStatus(HttpStatus.METHOD_NOT_ALLOWED);
        httpResponse.flush();
    }

}
