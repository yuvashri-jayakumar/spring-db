package com.demo.java;

import com.demo.java.config.BeanConfig;
import com.demo.java.dao.EmployeeDao;
import com.demo.java.model.Address;
import com.demo.java.model.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;


public class JavaConfigApplication {
    public static void main(String[] args) {
       runApplicationFromJavaConfig();
    }

    private static void runApplicationFromJavaConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        EmployeeDao dao = context.getBean(EmployeeDao.class);
        Address addr = new Address();
        addr.setStreet("123 Ambedkar St");
        addr.setCity("Chennai");
        addr.setState("TN");
        addr.setZip("600001");

        Employee emp = new Employee();
        emp.setName("Jaya Kumar");
        emp.setDepartment("IT");
        emp.setSalary(75000);
        emp.setAddress(addr);

         dao.create(emp);
        System.out.println("Employee saved!");

        Employee e2 = dao.findById(7);
        System.out.println("Employee fetched: " + e2.getName() + " from " + e2.getAddress().getCity());

        List<Employee> employeeList = dao.findAll();
        employeeList.forEach(System.out::println);

        Address address = new Address();
        address.setStreet("123 Ambedkar St");
        address.setCity("Trichy");
        address.setState("TN");
        address.setZip("600001");
        Employee emp1 = new Employee();
        emp1.setName("Naveen Kumar");
        emp1.setDepartment("IT");
        emp1.setSalary(275000);
        emp1.setAddress(address);
        dao.update(emp1);

        dao.deleteById(11);
    }


}