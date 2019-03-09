package persistence.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import persistence.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EmployeeJdbcRepository implements IRepository<Integer, Employee> {
    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public EmployeeJdbcRepository(Properties props){
        logger.info("Initializing EmployeeRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from emplyees")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(Employee entity) {
        logger.traceEntry("saving employee {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into emplyees values (?,?,?)")){
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting employee with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from emplyees where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Employee entity) {
        logger.traceEntry("updating employee with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update emplyees set id=?,username=?,password=? where id=?")){
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
            preStmt.setInt(4,entity.getId());

            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit();
    }

    @Override
    public Employee findOne(Integer integer) {
        logger.traceEntry("finding employee with id {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from SortingTasks where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    Employee employee = new Employee(id, username, password);
                    logger.traceExit(employee);
                    return employee;
                }
            }
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        logger.traceExit("No employee found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Employee> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Employee> employees=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from emplyees")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");

                    Employee employee = new Employee(id, username, password);
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(employees);
        return employees;
    }


}
