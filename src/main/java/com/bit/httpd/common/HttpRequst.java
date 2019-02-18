package com.bit.httpd.common;

//客户端（浏览器）的请求

/*
请求包含3部分
1. 首行：
Get URL(http://zhidao.baidu.com/search?key1=valu1&key2=value2&key1=value3) HTTP/1.1
2.协议头：
 key1: vaule1
 key1: value2
 key3: vaule3   //是以冒号分割的键值对，并且每个键值对后\r\n

还要考虑到url里请求查询的字符串即参数 是以=相连的键值对

 */

import java.util.List;
import java.util.Map;

public interface HttpRequst {

    HttpMethod method();  //获得请求方法 Get或者Post

    Object getContextValue(String value);  //可以获取

    String url();  //获得url

    String version(); //获得http协议版本

    Map<String,String> header(); //协议头

    Map<String,List<String>> parmas();
    //请求查询的字符串，第二个参数用list是因为一个key可能对应多个value

}
