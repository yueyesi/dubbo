package com.hzhang.dubbo.user;

import com.hzhang.dubbo.order.DoOrderRequest;
import com.hzhang.dubbo.order.DoOrderResponse;
import com.hzhang.dubbo.order.IOrderServices;
import javassist.ClassPath;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
