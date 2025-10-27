package com.demo.xml.dao.impl;

import com.demo.xml.dao.EmployeeDao;
import com.demo.xml.model.Address;
import com.demo.xml.model.Employee;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

public class EmployeeDaoImpl implements EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void create(Employee employee) {
        String addressSql = "insert into address (street, city, state, zip) values (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                    PreparedStatement preparedStatement = connection.prepareStatement(addressSql, Statement.RETURN_GENERATED_KEYS);
                    preparedStatement.setString(1,employee.getAddress().getStreet());
                    preparedStatement.setString(2,employee.getAddress().getCity());
                    preparedStatement.setString(3,employee.getAddress().getState());
                    preparedStatement.setString(4,employee.getAddress().getZip());
                    return preparedStatement;
                },keyHolder);
        int addressId= Objects.requireNonNull(keyHolder.getKey()).intValue();
        String sql = "insert into employee (name, department, salary, address_id) values (?,?,?,?)";
        jdbcTemplate.update(sql, employee.getName(), employee.getDepartment(), employee.getSalary(),addressId);

    }

    @Override
    public Employee findById(int id) {
        String sql = "SELECT e.*, a.street, a.city, a.state, a.zip FROM Employee e JOIN Address a ON e.address_id = a.id WHERE e.id = "+id;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            Address address = new Address();
            address.setId(rs.getInt("address_id"));
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setZip(rs.getString("zip"));

            Employee emp = new Employee();
            emp.setId(rs.getInt("id"));
            emp.setName(rs.getString("name"));
            emp.setDepartment(rs.getString("department"));
            emp.setSalary(rs.getDouble("salary"));
            emp.setAddress(address);
            return emp;
        });
    }

    @Override
    public List<Employee> findAll() {
        String sql = "select e.id, e.name, e.department, e.salary, " +
                "a.id AS address_id, a.street, a.city, a.state, a.zip " +
                "from Employee e " +
                "join Address a on e.address_id = a.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Address address = new Address();
            address.setId(rs.getInt("address_id"));
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setState(rs.getString("state"));
            address.setZip(rs.getString("zip"));

            Employee emp = new Employee();
            emp.setId(rs.getInt("id"));
            emp.setName(rs.getString("name"));
            emp.setDepartment(rs.getString("department"));
            emp.setSalary(rs.getDouble("salary"));
            emp.setAddress(address);
            return emp;
        });
    }

    @Override
    @Transactional
    public void update(Employee employee) {
        String addressSql = "update address set street = ?, city = ?, state = ?, zip = ? where id = ?";
        jdbcTemplate.update(addressSql,
                employee.getAddress().getStreet(),
                employee.getAddress().getCity(),
                employee.getAddress().getState(),
                employee.getAddress().getZip(),
                employee.getAddress().getId()
        );

        String employeeSql = "update Employee set name = ?, department = ?, salary = ? where id = ?";
        jdbcTemplate.update(employeeSql,
                employee.getName(),
                employee.getDepartment(),
                employee.getSalary(),
                employee.getId()
        );
    }

    @Override
    @Transactional(rollbackFor = SQLServerException.class)
    public void deleteById(int id) {
        Integer addressId = jdbcTemplate.queryForObject("select address_id from Employee where id = ?", Integer.class,id);
        String addressSql = "delete from Address where id=";
        String employeeSql = "delete from Employee where id=?";

        jdbcTemplate.update(employeeSql,id);
        jdbcTemplate.update(addressSql,addressId);
    }


}
