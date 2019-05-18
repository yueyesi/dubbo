package com.hzhang.dubbo.serviceconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hzhang.dubbo.serviceapi.IService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @Reference
    private IService iService;

    @RequestMapping("/home")
    public void home(){
       boolean  flag=iService.order("测试");
        System.out.println(flag);
    }
}
