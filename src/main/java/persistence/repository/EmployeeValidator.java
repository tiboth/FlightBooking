package persistence.repository;

import persistence.model.Employee;

public class EmployeeValidator implements Validator<Employee> {

    @Override
    public void validate(Employee employee) throws ValidationException {
        StringBuffer msg=new StringBuffer();
        if (employee.getId()<0)
            msg.append("Id-ul nu poate fi negativ: " + employee.getId());
          if (msg.length() > 0)
            throw new ValidationException(msg.toString());
    }
}
