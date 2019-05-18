package com.hzhang.dubbo.order;

import java.io.Serializable;

public class DoOrderRequest implements Serializable{
    private static final long serialVersionUID = 5806083519781762287L;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DoOrderRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
