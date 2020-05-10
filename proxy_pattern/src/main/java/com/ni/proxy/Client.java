package com.ni.proxy;

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
         *  分类：基于接口的动态代理
         *          涉及的类：Proxy
         *          提供者：JDK官方
         *          如何创建代理对象：使用Proxy中的newProxyInstance方法
         *          要求：被代理类最少实现一个接口，如果没有则不能使用
         *          newProxyInstance方法的参数：
         *              1. ClassLoader:类加载器，用于加载代理对象的字节码，和被代理对象使用相同的类加载器（固定写法）
         *              2. Class[] 字节码数组，用于让代理对象和被代理对象有相同的方法（固定写法）
         *              3. InvocationHandler:用于提供增强的代码，写如何代理，一般写一个该接口的实现类，通常情况下用匿名内部类
         *
         *        基于子类的动态代理
         */
        IProducer proxyP = (IProducer) Proxy.newProxyInstance(p.getClass().getClassLoader(), p.getClass().getInterfaces(), new InvocationHandler() {
            /**
             *
             * @param proxy 代理对象的引用
             * @param method 当前执行的方法
             * @param args 当前执行方法所需的参数
             * @return 和被代理对象方法有相同的返回值
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //提供增强的代码
                Object returnValue = null;
                Float money = (Float)args[0]*0.8f;
                if("saleProduct".equals(method.getName()))
                    returnValue =  method.invoke(p,money);
                return returnValue;
            }
        });
        proxyP.saleProduct(10000);
    }
}
