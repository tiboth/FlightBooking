package persistence.repository;

import persistence.model.Employee;
import persistence.model.Flight;

public class FlightValidator implements Validator<Flight> {

    @Override
    public void validate(Flight flight) throws ValidationException {
        StringBuffer msg=new StringBuffer();
        if (flight.getId()<0)
            msg.append("Id-ul nu poate fi negativ: " + flight.getId());
        if (msg.length() > 0)
            throw new ValidationException(msg.toString());
    }
}