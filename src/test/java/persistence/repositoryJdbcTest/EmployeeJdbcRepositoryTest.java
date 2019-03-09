package persistence.repositoryJdbcTest;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import persistence.model.Employee;
import persistence.repository.EmployeeJdbcRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class EmployeeJdbcRepositoryTest {

    private EmployeeJdbcRepository employeeJdbcRepository;

    public EmployeeJdbcRepositoryTest() {
        Properties serverProps = new Properties();
        try {
            serverProps.load(new FileReader("db.config"));
            //System.setProperties(serverProps);

            System.out.println("Properties set. ");
            //System.getProperties().list(System.out);
            serverProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find db.config "+e);
        }
        employeeJdbcRepository = new EmployeeJdbcRepository(serverProps);
    }

    @After
    public void wipeTableEmployee() {
        employeeJdbcRepository.deleteAll();
    }

    @Test
    public void testSizeEmployee() {
        employeeJdbcRepository.deleteAll();
        Assert.assertEquals(0,employeeJdbcRepository.size());
    }

    @Test
    public void testSaveEmployee() {
        //DELETE all rows from employees
        employeeJdbcRepository.deleteAll();
        Assert.assertEquals(0,employeeJdbcRepository.size());

        //Test SAVE
        employeeJdbcRepository.save(createEmployee1());
        Assert.assertEquals(1,employeeJdbcRepository.size());
    }

    @Test
    public void testDeleteEmplyee() {
        //delete all rows from employee
        employeeJdbcRepository.deleteAll();
        Assert.assertEquals(0,employeeJdbcRepository.size());

        //save
        employeeJdbcRepository.save(createEmployee1());
        Assert.assertEquals(1,employeeJdbcRepository.size());

        //find all
        List<Employee> employeesList = Lists.newArrayList(employeeJdbcRepository.findAll());

        //Test DELETE
        Assert.assertEquals(1,employeeJdbcRepository.size());
        employeeJdbcRepository.delete(employeesList.get(0).getId());
        Assert.assertEquals(0,employeeJdbcRepository.size());
    }

    @Test
    public void testDeleteAllEmployees() {
        employeeJdbcRepository.deleteAll();
        Assert.assertEquals(0,employeeJdbcRepository.size());
    }

    @Test
    public void testUpdateEmployee() {
        //delete all rows from employees
        employeeJdbcRepository.deleteAll();
        Assert.assertEquals(0,employeeJdbcRepository.size());

        //save
        employeeJdbcRepository.save(createEmployee1());
        Assert.assertEquals(1,employeeJdbcRepository.size());

        //find all
        List<Employee> employeeList = Lists.newArrayList(employeeJdbcRepository.findAll());

        //update
        employeeJdbcRepository.update(employeeList.get(0).getId(), updateEmployee2());

        //find all
        List<Employee> updatedEmployeeList = Lists.newArrayList(employeeJdbcRepository.findAll());

        //test UPDATE
        Assert.assertEquals("username2", updatedEmployeeList.get(0).getUsername());
        Assert.assertEquals("password2", updatedEmployeeList.get(0).getPassword());
    }

    @Test
    public void testFindOneEmployee() {
        //delete all rows from employees
        employeeJdbcRepository.deleteAll();
        Assert.assertEquals(0,employeeJdbcRepository.size());

        //save
        employeeJdbcRepository.save(createEmployee1());
        Assert.assertEquals(1,employeeJdbcRepository.size());

        //find all
        List<Employee> employeeList = Lists.newArrayList(employeeJdbcRepository.findAll());

        //find one
        Employee employee = employeeJdbcRepository.findOne(employeeList.get(0).getId());

        //test FINDE ONE
        Assert.assertEquals("username", employee.getUsername());
        Assert.assertEquals("password", employee.getPassword());


    }

    @Test
    public void testFindAllEmployees() {
        //delete all rows from employees
        employeeJdbcRepository.deleteAll();
        Assert.assertEquals(0,employeeJdbcRepository.size());

        //save
        employeeJdbcRepository.save(createEmployee1());
        Assert.assertEquals(1,employeeJdbcRepository.size());

        //find all
        List<Employee> employeeList = Lists.newArrayList(employeeJdbcRepository.findAll());

        //test FIND ALL
        Assert.assertEquals(1, employeeList.size());;
        Assert.assertEquals("username", employeeList.get(0).getUsername());
        Assert.assertEquals("password", employeeList.get(0).getPassword());

    }

    private Employee createEmployee1() {
        return Employee.builder().
                username("username").
                password("password").
                build();
    }

    private Employee updateEmployee2() {
        return Employee.builder().
                username("username2").
                password("password2").
                build();
    }
}
