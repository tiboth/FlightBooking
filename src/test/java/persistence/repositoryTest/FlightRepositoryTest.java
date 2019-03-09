package persistence.repositoryTest;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import persistence.model.Flight;
import persistence.repository.FlightRepository;
import persistence.repository.FlightValidator;
import persistence.repository.Validator;

import java.util.List;

public class FlightRepositoryTest {
    private FlightRepository flightRepository;

    public FlightRepositoryTest() {
        Validator<Flight> bookingValidator = new FlightValidator();
        flightRepository = new FlightRepository(bookingValidator);
    }

    @Test
    public void testSizeFlight() {
        Assert.assertEquals(8, flightRepository.size());
    }

    @Test
    public void testSaveFlight() {
        //test SAVE flight
        Assert.assertEquals(8, flightRepository.size());
        flightRepository.save(createFlight1());
        Assert.assertEquals(9, flightRepository.size());

        //delete added flight
        flightRepository.delete(9);
    }

    @Test
    public void testUpdateFlight() {
        //save flight
        flightRepository.save(createFlight1());

        //update flight
        flightRepository.update(9, updateFlight2());
        //find flight
        Flight flight = flightRepository.findOne(9);

        //test UPDATE flight
        Assert.assertEquals(9, flight.getId(), 0);
        Assert.assertEquals("2018/01/01", flight.getDepartureDate());
        Assert.assertEquals("17:00", flight.getDepartureTime());
        Assert.assertEquals("Beijing Capital Airport", flight.getAirport());
        Assert.assertEquals(300, flight.getPlaces());

        //delete flight
        flightRepository.delete(9);
    }

    @Test
    public void testDeleteFlight() {
        int initialSize = flightRepository.size();

        //save flight
        Assert.assertEquals(initialSize, flightRepository.size());
        flightRepository.save(createFlight1());
        Assert.assertEquals(initialSize + 1, flightRepository.size());

        //test DELETE flight
        flightRepository.delete(createFlight1().getId());
        Assert.assertEquals(initialSize, flightRepository.size());

    }

    @Test
    public void testFindOneFlight() {
        //save flight
        flightRepository.save(createFlight1());

        //test flight
        Flight flight = flightRepository.findOne(9);

        Assert.assertEquals(9, flight.getId(), 0);
        Assert.assertEquals("2019/03/09", flight.getDepartureDate());
        Assert.assertEquals("15:00", flight.getDepartureTime());
        Assert.assertEquals("Tokyo International Airport", flight.getAirport());
        Assert.assertEquals(250, flight.getPlaces());

        //delete flight
        flightRepository.delete(9);
    }

    @Test
    public void testFindAllFlights() {
        int initialSize = flightRepository.size();

        //test BOOKING
        Assert.assertEquals(initialSize, flightRepository.size());
        List<Flight> bookingList = Lists.newArrayList(flightRepository.findAll());
        Assert.assertEquals(initialSize, bookingList.size());
    }

    private Flight createFlight1() {
        return Flight.builder().
                id(9).
                departureDate("2019/03/09").
                departureTime("15:00").
                airport("Tokyo International Airport").
                destination("New York").
                places(250).
                build();
    }

    private Flight updateFlight2() {
        return Flight.builder().
                id(9).
                departureDate("2018/01/01").
                departureTime("17:00").
                airport("Beijing Capital Airport").
                destination("Singapore").
                places(300).
                build();
    }
}
