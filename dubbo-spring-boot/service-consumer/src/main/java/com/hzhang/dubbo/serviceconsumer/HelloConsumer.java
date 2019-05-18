package com.hzhang.dubbo.serviceconsumer;

import com.hzhang.dubbo.serviceapi.IService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Component;

@Component
public class HelloConsumer {
    @Reference
    private IService iService;

    public static void main(String[] args) {

    }

}