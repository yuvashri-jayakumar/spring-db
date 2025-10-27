package com.demo.xml;

import com.demo.xml.dao.EmployeeDao;
import com.demo.xml.model.Address;
import com.demo.xml.model.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;


public class XMLConfigApplication {
    public static void main(String[] args) {
       runApplicationFromXMLConfig();
    }

    private static void runApplicationFromXMLConfig() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean-config.xml");
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

       // dao.create(emp);
        System.out.println("Employee saved!");

        Employee e2 = dao.findById(7);
        System.out.println("Employee fetched: " + e2.getName() + " from " + e2.getAddress().getCity());

        System.out.println("Listing All the Employees");
        List<Employee> employeeList = dao.findAll();
        employeeList.forEach(System.out::println);

        System.out.println("Update the Employee details");
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

        System.out.println("Deleting the Employee By id");
        dao.deleteById(12);


    }
}