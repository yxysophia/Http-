package com.bit.httpd.core;

import com.bit.httpd.biz.IndexHanlder;
import com.bit.httpd.biz.TimeHandler;
import com.bit.httpd.config.ServerConfig;
import com.bit.httpd.handler.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public abstract  class   HttpServer {

    private ServerConfig serverConfig;
    //设置一个全局共享Map,只要程序启动，就会将ServerConfig添加到Map集合中
    private final  Map<String,Object> context=new HashMap<>();  //只要这个程序启动，就

    //线程池里线程个数是cpu核数*2，如两个cpu,核数为4
    private final ExecutorService executorService= Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors() * 2, new ThreadFactory() {
                //一个自增的变量
                private final AtomicInteger count=new AtomicInteger(0);
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread=new Thread(r,"Thread-Hander"+count.getAndIncrement());
                    return thread;
                }
            });


    //通过构造方法获取ServerConfig
    public HttpServer(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void start()
    {
        //创建服务端Socket
        try {
            ServerSocket serverSocket=new ServerSocket(serverConfig.getPort());
            context.put("serverConfig",serverConfig); //将初始化的serverConfig添加到Map中
            System.out.println(String.format("Server start on 127.0.0.1: " +
                    "port=%d,static path=%s,please visit http://127.0.0.1:%d",serverConfig.getPort(),
                    serverConfig.getPath(),serverConfig.getPort()));
            DispatcherHandler dispatcherHandler=new DispatcherHandler(context);
            loadHandle(dispatcherHandler);
            while(true)
            {
                Socket socket=serverSocket.accept();
                executorService.execute(dispatcherHandler.Handle(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadHandle(DispatcherHandler dispatcherHandler)
    {

        //内置
        dispatcherHandler.resgisterHandler("404",NotFoundHanlder.class);
        dispatcherHandler.resgisterHandler("405",NotSupportMethodHandler.class);
        //业务
        regeisterHandler(dispatcherHandler);
    }
    public abstract void regeisterHandler(final DispatcherHandler dispatcherHandler);
}
