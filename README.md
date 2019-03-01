**Http服务器**

**1.技术**

 - Java（基本语法、Socket接口）
 - 多线程技术
 - Http协议

**2.环境**

 - JDK1.8
 - IDEA管理工具
 - Maven管理工具
 
**3.功能**
 
 - 支持静态资源处理
 - 支持动态程序处理
 
 **4.特点**
 
 - 采用线程池服务器可以接收多个浏览器的请求
 - 用户可以输入访问主机端口号和静态资源路径，在浏览器根据需求向服务器发送请求数据
 - 通过获取Socket输入流后解析请求数据，按照Http协议格式响应数据
 - 静态资源文件类型支持支持html、txt、js、jpg、png、map3等，请求方法支持GET和POST，错误资源会返回404或405
 - 可以作为一个库提供给需要嵌入式的Web服务器的程序使用

**5.实现**

 **5.1 抽象**
 

 - 请求报文格式抽象为请求对象
 - 响应报文格式抽象为响应对象
 - 业务处理逻辑抽象为处理对象
 - 抽象服务器的服务对象
 
 **5.2 步骤**
 
 - 分析HTTP协议的特点
 - 解析请求报文，封装为请求对象
 - 根据请求对象的信息，获取处理对象，处理业务逻辑
 - 根据业务处理结果，封装响应数据
 
**6.具体过程**
![](http.jpg)
**7.应用**
 - 编译打包源码 mvn  clean package
 - 启动程序（设置参数：端口，静态文件目录：
 

```
jar  -jar  java_httpd-1.0.0.jar   --port=8080  --path=D:\Java
```


**7.1静态资源**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190227125413620.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NvcGhpYV9feXU=,size_16,color_FFFFFF,t_70)
 **7.2 动态资源**
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190227130321938.png)
 **7.3 资源错误**
 ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190227130653165.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3NvcGhpYV9feXU=,size_16,color_FFFFFF,t_70)
**8.收获**

 - 学会使用Maven管理工具
 - 掌握HTTP协议的具体格式及如何解析数据响应数据
 - 将所学Java知识应用到实际，对自我知识掌握的检测
 
