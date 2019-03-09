package persistence.repositoryJdbcTest;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import persistence.model.Flight;
import persistence.repository.FlightJdbcRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class FlightJdbcRepositoryTest {

    private FlightJdbcRepository flightJdbcRepository;

    public FlightJdbcRepositoryTest() {
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
        flightJdbcRepository = new FlightJdbcRepository(serverProps);
    }

    @After
    public void wipeTableFlight() {
        flightJdbcRepository.deleteAll();
    }

    @Test
    public void testSizeFlight() {
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());
    }

    @Test
    public void testSaveFlight() {
        //DELETE all rows from flights
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());

        //Test SAVE
        flightJdbcRepository.save(createFlight1());
        Assert.assertEquals(1,flightJdbcRepository.size());
    }

    @Test
    public void testDeleteFlight() {
        //delete all rows from flights
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());

        //save
        flightJdbcRepository.save(createFlight1());
        Assert.assertEquals(1,flightJdbcRepository.size());

        //find all
        List<Flight> flightList = Lists.newArrayList(flightJdbcRepository.findAll());

        //Test DELETE
        Assert.assertEquals(1,flightJdbcRepository.size());
        flightJdbcRepository.delete(flightList.get(0).getId());
        Assert.assertEquals(0,flightJdbcRepository.size());
    }

    @Test
    public void testDeleteAllFlights() {
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());
    }

    @Test
    public void testUpdateFlight() {
        //delete all rows from flights
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());

        //save
        flightJdbcRepository.save(createFlight1());
        Assert.assertEquals(1,flightJdbcRepository.size());

        //find all
        List<Flight> flightList = Lists.newArrayList(flightJdbcRepository.findAll());

        //update
        flightJdbcRepository.update(flightList.get(0).getId(), updateFlight2());

        //find all
        List<Flight> updatedFlightList = Lists.newArrayList(flightJdbcRepository.findAll());

        //test UPDATE
        Assert.assertEquals("2018/01/01", updatedFlightList.get(0).getDepartureDate());
        Assert.assertEquals("17:00", updatedFlightList.get(0).getDepartureTime());
        Assert.assertEquals("Beijing Capital Airport", updatedFlightList.get(0).getAirport());
        Assert.assertEquals("Singapore", updatedFlightList.get(0).getDestination());
        Assert.assertEquals(300, updatedFlightList.get(0).getPlaces());
    }

    @Test
    public void testFindOneFlight() {
        //delete all rows from flights
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());

        //save
        flightJdbcRepository.save(createFlight1());
        Assert.assertEquals(1,flightJdbcRepository.size());

        //find all
        List<Flight> flightList = Lists.newArrayList(flightJdbcRepository.findAll());

        //find one
        Flight flight = flightJdbcRepository.findOne(flightList.get(0).getId());

        //test FINDE ONE
        Assert.assertEquals("2019/03/09", flight.getDepartureDate());
        Assert.assertEquals("15:00", flight.getDepartureTime());
        Assert.assertEquals("Tokyo International Airport", flight.getAirport());
        Assert.assertEquals("New York", flight.getDestination());
        Assert.assertEquals(250, flight.getPlaces());

    }

    @Test
    public void testFindAllFlights() {
        //delete all rows from flights
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());

        //save
        flightJdbcRepository.save(createFlight1());
        Assert.assertEquals(1,flightJdbcRepository.size());

        //find all
        List<Flight> flightList = Lists.newArrayList(flightJdbcRepository.findAll());

        //test FIND ALL
        Assert.assertEquals(1, flightList.size());
        Assert.assertEquals("2019/03/09", flightList.get(0).getDepartureDate());
        Assert.assertEquals("15:00", flightList.get(0).getDepartureTime());
        Assert.assertEquals("Tokyo International Airport", flightList.get(0).getAirport());
        Assert.assertEquals("New York", flightList.get(0).getDestination());
        Assert.assertEquals(250, flightList.get(0).getPlaces());

    }

    @Test
    public void testFindAllFlightsWithDestinationAndDate() {
        //delete all rows from flights
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());

        //save
        flightJdbcRepository.save(createFlight1());
        Assert.assertEquals(1,flightJdbcRepository.size());

        //find all
        List<Flight> flightList = Lists.newArrayList(flightJdbcRepository.findAllFlightsWithDestinationAndDate("New York", "2019/03/09"));

        //test FIND ALL
        Assert.assertEquals(1, flightList.size());
        Assert.assertEquals("2019/03/09", flightList.get(0).getDepartureDate());
        Assert.assertEquals("15:00", flightList.get(0).getDepartureTime());
        Assert.assertEquals("Tokyo International Airport", flightList.get(0).getAirport());
        Assert.assertEquals("New York", flightList.get(0).getDestination());
        Assert.assertEquals(250, flightList.get(0).getPlaces());

    }

    @Test
    public void testFindAllFlightsWithDestinationAndDateAndTime() {
        //delete all rows from flights
        flightJdbcRepository.deleteAll();
        Assert.assertEquals(0,flightJdbcRepository.size());

        //save
        flightJdbcRepository.save(createFlight1());
        Assert.assertEquals(1,flightJdbcRepository.size());

        //find all
        List<Flight> flightList = Lists.newArrayList(flightJdbcRepository.findAllFlightsWithDestinationAndDateAndTime("New York", "2019/03/09", "15:00"));

        //test FIND ALL
        Assert.assertEquals(1, flightList.size());
        Assert.assertEquals("2019/03/09", flightList.get(0).getDepartureDate());
        Assert.assertEquals("15:00", flightList.get(0).getDepartureTime());
        Assert.assertEquals("Tokyo International Airport", flightList.get(0).getAirport());
        Assert.assertEquals("New York", flightList.get(0).getDestination());
        Assert.assertEquals(250, flightList.get(0).getPlaces());

    }

    private Flight createFlight1() {
        return Flight.builder().
                departureDate("2019/03/09").
                departureTime("15:00").
                airport("Tokyo International Airport").
                destination("New York").
                places(250).
                build();
    }

    private Flight updateFlight2() {
        return Flight.builder().
                departureDate("2018/01/01").
                departureTime("17:00").
                airport("Beijing Capital Airport").
                destination("Singapore").
                places(300).
                build();
    }
}
