package com.ni.cglib;

import com.ni.proxy.IProducer;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模拟一个消费者
 */
public class Client {
    public static void main(String[] args) {
        final Producer p = new Producer();
        /**
         * 动态代理
         *  特点：字节码随用随创建，随用随加载
         *  作用： 不修改源码的基础上对源码进行加强
         *  分类：基于子类的动态代理
         *          涉及的类：Enhancer
         *          提供者：第三方cglib库
         *          如何创建代理对象：使用Enhancer中的create方法
         *          要求：被代理类不能是最终类
         *          create方法的参数：
         *              1. Class：字节码，用于指定被代理对象的字节码
         *              2. callback，用于提供增强的代码。一般用该接口的子接口实现类 MethodInterceptor
         */
        Producer proxyP = (Producer) Enhancer.create(p.getClass(), new MethodInterceptor(){
            /**
             * 执行被代理对象的任何方法都会经过该方法
             * @param o
             * @param method
             * @param objects 前三个参数和基于接口代理中invoke方法的参数是一样的
             * @param methodProxy：当前执行方法的代理对象（用不上）
             * @return ：和被代理对象方法的返回值一致
             * @throws Throwable
             */
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object returnValue = null;
                Float money = (Float)objects[0]*0.8f;
                if("saleProduct".equals(method.getName()))
                    returnValue =  method.invoke(p,money);
                return returnValue;
            }
        });
        proxyP.saleProduct(12000f);
    }
}
