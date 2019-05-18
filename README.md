# dubbo
[toc]
## spring集成dubbo
项目结构：dubbo-user:消费者；dubbo-provider:生产者


### 1. 创建项目
`apache.maven.archetypes:maven-archetype-quickstart
`
```
dubbo
  dubbo-user
  dubbo-order
     order-provider
     order-api
```
maven项目类型： .
**创建子module时注意：** 鼠标选中父project---> new module

###  2. order-api中提供一个下单接口
```
public interface IOrderServices {
    DoOrderResponse doOrder(DoOrderRequest request);
}

```

### 3. order-provider实现下单接口

```
public class OrderServiceImpl implements IOrderServices {
    @Override
    public DoOrderResponse doOrder(DoOrderRequest request) {
        System.out.println("曾经来过： "+request);
        DoOrderResponse response = new DoOrderResponse();
        response.setCode("1000");
        response.setMemo("处理成功");
        return response;
    }
}
```

#### 引入dubbo依赖和api依赖(dubbo依赖于netty和spring)
```
    <dependencies>
        <dependency>
            <groupId>com.hzhang.dubbo.order</groupId>
            <artifactId>order-api</artifactId>
            <!--这个版本号必须加否则会出现问题-->
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.5.3</version>
        </dependency>
    </dependencies>
```
##### order-api 引入失败解决方法
```
1. 必须加上版本号，否则失败
2. setting --> ignored files ,有时候会忽略pom文件
```
#### 配置dubbo resources/order-provider.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context-3.0.xsd
        http://code.alibabatech.com/schema/dubbo
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd" default-autowire="byName">

    <!-- 提供方应用信息，用于计算依赖关系; own: 应用维护者 -->
    <dubbo:application name="order-provider" owner="mic" />

    <!-- 注册中心暴露服务地址 N/A表示不使用注册中心 -->
    <!--<dubbo:registry address="N/A" />-->
    <dubbo:registry address="zookeeper://39.108.75.44:2181" />

    <!-- 当前服务发布依赖的协议：webservice,thrift,hessain,http ； 用dubbo协议在20880端口暴露服务 -->
    <dubbo:protocol name="dubbo" port="20880" />

    <!--服务发布的配置，需要暴露的服务接口-->
    <dubbo:service interface="com.hzhang.dubbo.order.IOrderServices" ref="orderService"/>

    <!--bean定义-->
    <bean id="orderService" class="com.hzhang.dubbo.order.OrderServiceImpl"></bean>
</beans>
```
#### 配置日志：resources/log4j.properties

#### 启动服务类 App
```
public class App 
{
    public static void main( String[] args )
    {
        com.alibaba.dubbo.container.Main.main(args);
    }
}
```

### 4. dubbo-user实现调用

#### 引入依赖

#### 实现dubbo配置 resources/order-consumber
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context-3.0.xsd
        http://code.alibabatech.com/schema/dubbo
        http://code.alibabatech.com/schema/dubbo/dubbo.xsd" default-autowire="byName">

    <!-- 提供方应用信息，用于计算依赖关系; own: 应用维护者 -->
    <dubbo:application name="order-provider" owner="mic" />

    <!-- 注册中心暴露服务地址 N/A表示不使用注册中心 -->
    <dubbo:registry address="zookeeper://39.108.75.44:2181" />
    <!--<dubbo:registry address="zookeeper://192.168.0.107:2181" />-->
    <!--生成一个远程服务的调用代理-->
    <!---->
    <!--<dubbo:reference id="orderServices" interface="com.hzhang.dubbo.order.IOrderServices" url="dubbo://192.168.100.102:20880/com.hzhang.dubbo.order.IOrderServices"/>-->
    <dubbo:reference id="orderServices" interface="com.hzhang.dubbo.order.IOrderServices" />
</beans>
```

#### 实现调用
```
public class App {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("order-consumer.xml");
        IOrderServices iOrderServices= (IOrderServices) context.getBean("orderServices");
        // 下单
        DoOrderRequest request=new DoOrderRequest();
        request.setName("mic");
        DoOrderResponse response =iOrderServices.doOrder(request);
        System.out.println(response);
    }
}
```
## spring boot集成dubbo