package persistence.repository;

import persistence.model.Flight;

import java.util.List;

public class FlightRepository extends AbstractRepository<Integer, Flight> {

    public FlightRepository(Validator<Flight> val) {
        super(val);

        save(new Flight(1, "2019/01/12", "15:00", "Tokyo International Airport", "New York", 250));
        save(new Flight(2, "2019/01/12", "21:00", "Tokyo International Airport", "New York", 250));
        save(new Flight(3, "2019/01/12", "22:00", "Tokyo International Airport", "New York", 250));
        save(new Flight(4, "2019/03/15", "15:00", "Beijing Capital Airport", "Singapore", 300));
        save(new Flight(5, "2019/03/15", "16:30:00", "Beijing Capital Airport", "Singapore", 300));
        save(new Flight(6, "2019/03/08", "15:00", "Cluj-Napoca International Airport", "Bucharest", 150));
        save(new Flight(7, "2019/06/06", "15:00", "Cluj-Napoca International Airport", "Bucharest", 150));
        save(new Flight(8, "2019/10/07", "21:00", "London Heathrow Airport", "Cluj-Napoca", 90));

    }
}
