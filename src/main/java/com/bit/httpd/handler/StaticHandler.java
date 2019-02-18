package com.bit.httpd.handler;

import com.bit.httpd.common.Handler;
import com.bit.httpd.common.HttpRequst;
import com.bit.httpd.common.HttpResponse;
import com.bit.httpd.config.ServerConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

public class StaticHandler extends HandlerAdapter {

    @Override
    public void doGet(HttpRequst httpRequst, HttpResponse httpResponse) {

        ServerConfig serverConfig=(ServerConfig)httpRequst.getContextValue("serverConfig");
        //先获取态静态文件的存放路径
        String url=httpRequst.url();
        //  static/index.html
        File file=new File(serverConfig.getPath(),url);  //root下的子目录
        try {
            FileInputStream in=new FileInputStream(file);
            byte[] buff=new byte[1024];
            int len;
            while((len=in.read(buff))!=-1)
            {
                //因为不一定每次读取的是1024字节，所以需要将len的长度新拷贝到一个数组
                httpResponse.write(Arrays.copyOf(buff,len));
            }
        } catch (Exception e) {
            System.out.println("没有该文件："+file);
        }
        httpResponse.flush();
    }

}
