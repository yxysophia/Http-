package com.bit.httpd.core;


//解析数据的类

import com.bit.httpd.common.HttpRequst;
import com.bit.httpd.common.HttpResponse;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

public class HttpReqRespWrappr {
    private HttpResponse httpResponse;
    private HttpRequst httpRequest;
    private Socket socket;

    public HttpReqRespWrappr(Socket socket) {
        this.socket = socket;
        this.parseHander(); //调请求解析数据方法
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public HttpRequst getHttpRequest() {
        return httpRequest;
    }

    //解析数据
    public void parseHander()
    {
        Map<String,String> line=new HashMap<>();
        Map<String,List<String>> parmas=new HashMap<>();
        Map<String,String> header=new HashMap<>();

        try {
            //获取socket的输入流
            Scanner in=new Scanner(socket.getInputStream());

            StringTokenizer stringTokenizer=null;
            //1.获取首行
            if(in.hasNext())
            {
                String lineValue=in.nextLine();  //首行  Get URL HTTP/1.1
                stringTokenizer=new StringTokenizer(lineValue); //默认以空格分割
            }
            //向首行Map中添加浏览器的请求方法
            if(stringTokenizer!=null&& stringTokenizer.hasMoreTokens())
            {
                String method=stringTokenizer.nextToken();
                line.put("method",method);
            }
            //向首行Map中添加URL或者参数中添加信息
            if(stringTokenizer!=null && stringTokenizer.hasMoreTokens())
            {
                String url=stringTokenizer.nextToken(); //获取url
                //将URL获取结束后，需要判断URL里是否有查询字符串即参数
                //URL:http://zhidao.baidu.com/search?key1=valu1&key2=value2&key1=value3
                int index=url.indexOf('?');
                if(index!=-1)  //也就是有查询字符串,需要将查询字符串进行解码
                    //////有问题
                    ///有问题
                {
                     parmas=decodeParameters(url.substring(index+1)); //将？后的查询字符串进行解码
                     line.put("url",url.substring(0,index));
                }
                else
                {
                    line.put("url",url);
                }
            }
            if(stringTokenizer!=null && stringTokenizer.hasMoreTokens())
            {
                //获取http协议版本
                String version=stringTokenizer.nextToken();
                line.put("version",version);
            }
//            System.out.println("首行获取完毕");
            //获取协议头
            String headerValue=null;
            if(in.hasNext())
            {
                headerValue =in.nextLine();  //读取协议头信息
            }
            while(headerValue!=null&&!headerValue.trim().isEmpty()) {
                String key = headerValue.split(":")[0];
                String value = headerValue.split(":")[1];
                header.put(key, value);
                headerValue = in.nextLine();
            }
            //给httpRequst和httpResponse赋值
            httpRequest=new DefaultHttpRequst(line,header,parmas);
            httpResponse=new DefaultHttpResponse(httpRequest,socket);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //将查询字符串添加到集合中
    public static Map<String,List<String>> decodeParameters(String queryString)
    {
        Map<String,List<String>> parmas=new HashMap<>();

        //查询字符串是?key1=valu1&key2=value2&key1=value3
        if(queryString!=null)
        {
            StringTokenizer stringTokenizer=new StringTokenizer(queryString,"&");//以&分割
            while(stringTokenizer.hasMoreTokens())
            {
                String kv=stringTokenizer.nextToken();
                String key=decodePercent(kv.split("=")[0]);
                String vaule=decodePercent(kv.split("=")[1]);
                //如果key值已经存在，只需要把value进行添加
                if(parmas.containsKey(key))
                {
                    List<String> vaules=parmas.get(key);
                    vaules.add(vaule);
                }
                else
                {
                    //如果key值不存在
                    List<String> values=new ArrayList<>();
                    values.add(vaule);
                    parmas.put(key,values);
                }
            }
            return parmas;
        }
        return null;
    }
    //解码
    public static String decodePercent(String str)
    {
        String decode=null;
        try {
            decode=URLDecoder.decode(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decode;
    }
}
