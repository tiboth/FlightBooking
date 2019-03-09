package persistence.repositoryTest;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import persistence.model.Employee;
import persistence.repository.*;

import java.util.List;

public class EmployeeRepositoryTest {
    private EmployeeRepository employeeRepository;

    public EmployeeRepositoryTest() {
        Validator<Employee> employeeValidator = new EmployeeValidator();
        employeeRepository = new EmployeeRepository(employeeValidator);
    }

    @Test
    public void testSizeEmployee() {
        Assert.assertEquals(2, employeeRepository.size());
    }

    @Test
    public void testSaveEmployee() {
        //test SAVE employee
        Assert.assertEquals(2, employeeRepository.size());
        employeeRepository.save(createEmployee1());
        Assert.assertEquals(3, employeeRepository.size());

        //delete added employee
        employeeRepository.delete(3);
    }

    @Test
    public void testUpdateEmployee() {
        //save employee
        employeeRepository.save(createEmployee1());

        //update employee
        employeeRepository.update(3, updateEmployee2());
        //find employee
        Employee employee = employeeRepository.findOne(3);

        //test UPDATE employee
        Assert.assertEquals(3, employee.getId(), 0.0);
        Assert.assertEquals("username2", employee.getUsername());
        Assert.assertEquals("password2", employee.getPassword());

        //delete employee
        employeeRepository.delete(3);
    }

    @Test
    public void testDeleteEmployee() {
        int initialSize = employeeRepository.size();

        //save employee
        Assert.assertEquals(initialSize, employeeRepository.size());
        employeeRepository.save(createEmployee1());
        Assert.assertEquals(initialSize + 1, employeeRepository.size());

        //test DELETE emplyee
        employeeRepository.delete(createEmployee1().getId());
        Assert.assertEquals(initialSize, employeeRepository.size());

    }

    @Test
    public void testFindOneEmployee() {
        //save mployee
        employeeRepository.save(createEmployee1());

        //test FIND ONE employee
        Employee employee = employeeRepository.findOne(3);

        Assert.assertEquals(3, employee.getId(), 0.0);
        Assert.assertEquals("username", employee.getUsername());
        Assert.assertEquals("password", employee.getPassword());

        //delete booking
        employeeRepository.delete(3);
    }

    @Test
    public void testFindAllEmployee() {
        int initialSize = employeeRepository.size();

        //test FIND ALL bookings
        Assert.assertEquals(initialSize, employeeRepository.size());
        List<Employee> employeeList = Lists.newArrayList(employeeRepository.findAll());
        Assert.assertEquals(initialSize, employeeList.size());
    }

    private Employee createEmployee1() {
        return Employee.builder().
                id(3).
                username("username").
                password("password").
                build();
    }

    private Employee updateEmployee2() {
        return Employee.builder().
                id(3).
                username("username2").
                password("password2").
                build();
    }
}
