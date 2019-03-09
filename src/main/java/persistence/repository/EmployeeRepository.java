package persistence.repository;

import persistence.model.Employee;

public class EmployeeRepository extends AbstractRepository<Integer, Employee> {

    public EmployeeRepository(Validator<Employee> val) {
        super(val);

        save(new Employee(1,"admin","admin"));
        save(new Employee(2,"bothtihamer","pass"));
    }
}
