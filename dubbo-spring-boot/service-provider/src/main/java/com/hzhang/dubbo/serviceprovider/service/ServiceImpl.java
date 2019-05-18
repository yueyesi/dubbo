package com.hzhang.dubbo.serviceprovider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hzhang.dubbo.serviceapi.IService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = IService.class)
@Component
public class ServiceImpl implements IService{


    @Override
    public boolean order(String name) {
        System.out.println("调用下单服务");
        return false;
    }
}
