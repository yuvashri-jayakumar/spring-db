package com.demo.java.config;

import com.demo.java.dao.EmployeeDao;
import com.demo.java.dao.impl.EmployeeDaoImpl;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class BeanConfig {


    @Bean
    public SQLServerDataSource dataSource(){
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setURL("jdbc:sqlserver://localhost:1433;databaseName=grocery;encrypt=true;trustServerCertificate=true");
        ds.setUser("marudhu.manickam");
        ds.setPassword("whatever");
        return ds;

    }

    @Bean
    public JdbcTemplate jdbcTemplate(){
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public EmployeeDao employeeDao(){
        EmployeeDaoImpl dao = new EmployeeDaoImpl();
        dao.setJdbcTemplate(jdbcTemplate());
        return dao;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(){
       return new DataSourceTransactionManager(dataSource());
    }
}
