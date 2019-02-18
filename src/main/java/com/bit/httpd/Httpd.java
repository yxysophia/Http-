package com.bit.httpd;

import com.bit.httpd.biz.IndexHanlder;
import com.bit.httpd.biz.TimeHandler;
import com.bit.httpd.config.ServerConfig;
import com.bit.httpd.core.DispatcherHandler;
import com.bit.httpd.core.HttpServer;
import com.bit.httpd.handler.AboutHanlder;
import com.bit.httpd.handler.StaticHandler;

public class Httpd {
    public static void main(String[] args) {
        ServerConfig serverConfig=new ServerConfig();
        //通过main函数参数获取port和path
        for(String param:args)
        {
            if(param.contains("="))
            {
                int index=param.indexOf("=");
                String key=param.substring(0,index);
                String value=param.substring(index+1);
                if(key.equals("--port"))
                {
                    serverConfig.setPort(Integer.parseInt(value));
                }
                else if(key.equals("--path"))
                {
                    serverConfig.setPath(value);
                }
            }
        }
        HttpServer httpServer= new HttpServer(serverConfig) {
            //匿名内部类
            //模板模式设计思想，在模板在类中有抽象方法
            @Override
            public void regeisterHandler(DispatcherHandler dispatcherHandler) {
                dispatcherHandler.resgisterHandler("/about",AboutHanlder.class);
                dispatcherHandler.resgisterHandler("/time",TimeHandler.class);
                //因为静态都是index.html  或者index.jpg  所以用一个特殊字符串default_static来标识是静态文件
                dispatcherHandler.resgisterHandler("default_static",StaticHandler.class);
                dispatcherHandler.resgisterHandler("/about",AboutHanlder.class);
                dispatcherHandler.resgisterHandler("/",IndexHanlder.class);
            }
        };
        httpServer.start();
    }
}