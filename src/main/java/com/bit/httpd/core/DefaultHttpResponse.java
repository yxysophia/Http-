package com.bit.httpd.core;

import com.bit.httpd.common.HttpRequst;
import com.bit.httpd.common.HttpResponse;
import com.bit.httpd.common.HttpStatus;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

//在响应时头信息包含内容类型 是jpg png html等等
public class DefaultHttpResponse implements HttpResponse {

    private final HttpRequst httpRequst;
    private final Socket socket;
    private HttpStatus httpStatus=HttpStatus.OK; //默认为OK
    private Map<String,String> header=new HashMap<>(); // 协议头
    private ByteArrayOutputStream content=new ByteArrayOutputStream();  //写出去的内容
    private Map<String,Object> context=new HashMap<>();  //上下文

    public DefaultHttpResponse(HttpRequst httpRequst, Socket socket) {
        this.httpRequst = httpRequst;
        this.socket = socket;
    }

    @Override
    public void setHttpStatus(HttpStatus httpStatus) {
            this.httpStatus=httpStatus;
    }

    public void setContext(Map<String,Object> context)
    {
        this.context=context;
    }

    @Override
    public Object getContextValue(String value) {
        return context.get(value);
    }

    @Override
    public void setHeader(String key, String value) {
        this.header.put(key,value);

    }
    @Override
    public void setContentType(String value) {
        this.header.put("Content-type",value);

    }
    @Override
    public void setVersion(HttpRequst httpRequst) {

    }

    @Override
    public void write(byte[] value) {
        try {
            content.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String value) {
        try {
            content.write(value.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //将内容写到浏览器
//    响应分为：
//            1.首行： HTTP/1.1 状态码（200） 状态码解释
//2.协议头：
//    key1: vaule1
//    key1: value2
//    key3: vaule3   //是以冒号分割的键值对，并且每个键值对后\r\n
//3.空行 \r\n
//4.正文
    @Override
    public void flush() {
        try {
            OutputStream out=socket.getOutputStream();
            //1.首行
            //将首行信息转为字节数组
            out.write(encodeContent(this.httpRequst.version()+" "+
                    httpStatus.getRequestStatus()+" "+
                    httpStatus.getDescription()+"\r\n"));
            //2.协议头
           for(Map.Entry<String,String> entry:header.entrySet())
           {
               out.write(encodeContent(entry.getKey()+entry.getValue()+"\r\n"));
           }
           //3.空行
            out.write(encodeContent("\r\n"));
           //4.正文,写content
            ByteArrayInputStream in=new ByteArrayInputStream(content.toByteArray());
            byte[] buff=new byte[1024];
            int len;
            while((len=in.read(buff))!=-1)
            {
                out.write(buff,0,len);
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static byte[] encodeContent(String value) {
        return value.getBytes();
    }
}
