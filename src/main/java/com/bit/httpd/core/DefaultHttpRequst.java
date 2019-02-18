package com.bit.httpd.core;

import com.bit.httpd.common.HttpMethod;
import com.bit.httpd.common.HttpRequst;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//请求：需要首行 协议头 参数（首行里的查询字符串）

public class DefaultHttpRequst implements HttpRequst {

    private Map<String,String> line;
    //首行的Map ,将请求方法 URL 版本号分别用key value形式

    private Map<String,String> header; //协议头的键值对
    private Map<String,List<String>> parmas; //参数（URL里的查询字符串）

    private Map<String,Object> context=new HashMap<>(); //存放ServerConfig对象，也就是服务器配置信息

    public DefaultHttpRequst(Map<String, String> line, Map<String, String> header, Map<String, List<String>> parmas) {
        this.line = line;
        this.header = header;
        this.parmas = parmas;
    }

    @Override
    public HttpMethod method() {
        //获取客户端请求的方法 ，首先从首行中get到方法，然后在再HttpMethod返回方法对象
        return HttpMethod.loonUp(line.get("method"));
    }

    public void setContext(Map<String,Object> context)
    {
        this.context=context;
    }
    @Override
    public Object getContextValue(String value) {
       return  context.get(value);
    }

    @Override
    public String url() {
        return line.get("url");
    }

    @Override
    public String version() {
        //如果版本号为空，默认是HTTP/1.1
        String version=line.get("version");
        if(version==null)
            return "HTTP/1.1";
        return version;
    }

    @Override
    public Map<String, String> header() {
        return header;
    }

    @Override
    public Map<String, List<String>> parmas() {
        return parmas;
    }
}
