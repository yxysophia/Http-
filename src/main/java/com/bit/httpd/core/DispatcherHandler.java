package com.bit.httpd.core;

//对响应进行处理的类  根据不同的请求进行处理，判断是否是静态文件
import com.bit.httpd.common.*;

import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DispatcherHandler {

    private  static int count=0;
    //被final修饰的变量是常量
    private final Map<String,Handler> handlers=new HashMap<>();

    private Map<String,Object> context=new HashMap<>();  //上下文数据，即存放服务器配置

    public DispatcherHandler(Map<String, Object> context) {
        this.context = context;
    }

    //注册
    public void resgisterHandler (String url, Class<? extends Handler> classes)
    {
        try {
            //通过反射来获取到Handler对象
            Handler handler=classes.getConstructor().newInstance();
            handlers.put(url,handler); //将所有handlers注册到map集合中
        } catch (Exception e) {
            e.printStackTrace();
        };

    }
    public Runnable Handle(Socket socket)
    {
        //找到客户端的请求和服务器的响应,实例化HttpReqRespWrappr对象会得到请求和响应
        HttpReqRespWrappr httpReqRespWrappr=new HttpReqRespWrappr(socket);

        DefaultHttpRequst httpRequst =(DefaultHttpRequst)httpReqRespWrappr.getHttpRequest();
        httpRequst.setContext(context);
        DefaultHttpResponse httpResponse=(DefaultHttpResponse)httpReqRespWrappr.getHttpResponse();
        httpResponse.setContext(context);
        return new Runnable() {


            //匿名内部类
            @Override
            public void run() {

                //先判断是否是静态
                if(isStaticSource(httpRequst.url()))
                {
                    //System.out.println("是静态");
                    //静态已经被注册，可以直接获取
                    handlers.get("default_static").doGet(httpRequst,httpResponse);
                }
                else
                {
                    //先取得如何处理对象
                    Handler handler=handlers.get(httpRequst.url());
                    //对象不为空，也就是添加到处理器map集合中
                    if(handler!=null)
                    {
                        //请求方法是GET
                        if(httpRequst.method()==(HttpMethod.GET))
                        {
                            //System.out.println("GET");
                            handler.doGet(httpRequst,httpResponse);

                        }
                        //请求方法是POST
                        else if(httpRequst.method()==(HttpMethod.POST))
                        {
                            // System.out.println("POST");
                            handler.doPost(httpRequst,httpResponse);
                        }
                        //没有匹配的请求方法，即405
                        else
                        {
                            //405处理
                            handlers.get("405").doGet(httpRequst,httpResponse);
                        }
                    }
                    //URL格式不对，服务器无法处理，即404
                    else
                    {
                        //404
                        handlers.get("404").doGet(httpRequst,httpResponse);

                    }
                }

            }
        };
    }

    //怕断是否是静态文件：index.html
    public boolean isStaticSource(String url)
    {

        //System.out.println("判断是否为静态："+url);

        if(url!=null)
        {
            int index=url.lastIndexOf(".");
            if(index==-1)
            {
                return false;  //不是静态文件
            }
            String extend=url.substring(index+1);  //获取扩展名
            if(SupportMimeType.lookup(extend)!=null)
            {
                return true;
            }
        }
       return false;
    }
}
