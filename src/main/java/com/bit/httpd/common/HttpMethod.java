package com.bit.httpd.common;


//请求的方法
public enum HttpMethod {

    GET,  //枚举对象
    POST;

    public static HttpMethod loonUp(String method)
    {
        //HttpMethod.values()返回的相当于是一个数组,是所有的HttpMethod对象
        for(HttpMethod httpMethod :HttpMethod.values())
        {
            if(httpMethod.name().equalsIgnoreCase(method))
            {
                return httpMethod;
            }
        }
        return null;
    }

}
