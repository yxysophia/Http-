package com.bit.httpd.config;


import java.io.File;

//服务器配置 访问服务器的端口号和静态文件路径
public class ServerConfig {
    private Integer port=80;  //端口号
    private String path=System.getProperty("user.dir")+ File.separator+"static";;  //静态文件路径

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
