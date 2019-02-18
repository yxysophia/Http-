package com.bit.httpd.common;


//服务器的响应
/*
响应分为：
1.首行： HTTP/1.1 状态码（200） 状态码解释
2.协议头：
 key1: vaule1
 key1: value2
 key3: vaule3   //是以冒号分割的键值对，并且每个键值对后\r\n
3.空行 \r\n
4.正文
 */

public interface HttpResponse {
    void setHttpStatus(HttpStatus httpStatus); //设置状态信息

    Object getContextValue(String value);

    void setHeader(String key,String value); //设置协议头

    void setVersion(HttpRequst httpRequst);  //协议版本号
    //正文
    void write(byte[] value); //以字节数组的形式写
    void write(String value); //以字符串的形式写

    void flush(); //服务器将写的正文发送到浏览器

    void setContentType(String value);  //

}

