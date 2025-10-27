package com.demo.xml.aop;


import org.aspectj.lang.JoinPoint;


public class DepartmentLoggingAspect {

    public void logAdvise(JoinPoint jp) {
        System.out.println("***Logging***"+ jp.getSignature());
    }

}
