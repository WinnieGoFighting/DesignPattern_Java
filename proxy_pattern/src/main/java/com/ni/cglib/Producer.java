package com.ni.cglib;

import com.ni.proxy.IProducer;

/**
 * 生产者
 */
public class Producer {
    public void saleProduct(float money) {
        System.out.println("销售产品，拿到钱: "+money);
    }

    public void afterService(float money) {
        System.out.println("提供售后服务，并拿到钱： "+money);
    }
}
