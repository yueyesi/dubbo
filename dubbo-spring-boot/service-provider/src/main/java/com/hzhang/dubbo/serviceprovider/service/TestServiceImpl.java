package com.hzhang.dubbo.serviceprovider.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.hzhang.dubbo.serviceapi.IService;
import com.hzhang.dubbo.serviceapi.ITestService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = ITestService.class)
@Component
public class TestServiceImpl implements ITestService {

    @Override
    public List<Map<String, Object>> getUserList() {
        System.out.println("获取用户列表");
        return null;
    }
}
