package persistence.repositoryJdbcTest;

import com.google.common.collect.Lists;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import persistence.model.Booking;
import persistence.repository.BookingJdbcRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class BookingJdbcRepositoryTest {

    private BookingJdbcRepository bookingJdbcRepository;

    public BookingJdbcRepositoryTest() {
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
        bookingJdbcRepository = new BookingJdbcRepository(serverProps);
    }

    @After
    public void wipeTableBooking() {
        bookingJdbcRepository.deleteAll();
    }

    @Test
    public void testSize() {
        bookingJdbcRepository.deleteAll();
        Assert.assertEquals(0,bookingJdbcRepository.size());
    }

    @Test
    public void testSaveBooking() {
        //DELETE all rows from bookings
        bookingJdbcRepository.deleteAll();
        Assert.assertEquals(0,bookingJdbcRepository.size());

        //Test SAVE
        bookingJdbcRepository.save(createBooking1());
        Assert.assertEquals(1,bookingJdbcRepository.size());
    }

    @Test
    public void testDeleteBooking() {
        //delete all rows from bookings
        bookingJdbcRepository.deleteAll();
        Assert.assertEquals(0,bookingJdbcRepository.size());

        //save
        bookingJdbcRepository.save(createBooking1());
        Assert.assertEquals(1,bookingJdbcRepository.size());

        //find all
        List<Booking> bookingsList = Lists.newArrayList(bookingJdbcRepository.findAll());

        //Test DELETE
        Assert.assertEquals(1,bookingJdbcRepository.size());
        bookingJdbcRepository.delete(bookingsList.get(0).getId());
        Assert.assertEquals(0,bookingJdbcRepository.size());
    }

    @Test
    public void testDeleteAll() {
        bookingJdbcRepository.deleteAll();
        Assert.assertEquals(0,bookingJdbcRepository.size());
    }

    @Test
    public void testUpdateBooking() {
        //delete all rows from bookings
        bookingJdbcRepository.deleteAll();
        Assert.assertEquals(0,bookingJdbcRepository.size());

        //save
        bookingJdbcRepository.save(createBooking1());
        Assert.assertEquals(1,bookingJdbcRepository.size());

        //find all
        List<Booking> bookingsList = Lists.newArrayList(bookingJdbcRepository.findAll());

        //update
        bookingJdbcRepository.update(bookingsList.get(0).getId(), updateBooking2());

        //find all
        List<Booking> updatedBookingsList = Lists.newArrayList(bookingJdbcRepository.findAll());

        //test UPDATE
        Assert.assertEquals(2, updatedBookingsList.get(0).getFlightId());
        Assert.assertEquals("UpdateName", updatedBookingsList.get(0).getClientName());
        Assert.assertEquals("UpdateAddress", updatedBookingsList.get(0).getClientAddress());
        Assert.assertEquals("updateTourist", updatedBookingsList.get(0).getTourists());
        Assert.assertEquals(1, updatedBookingsList.get(0).getNrSeats());

    }

    @Test
    public void testFindOneBooking() {
        //delete all rows from bookings
        bookingJdbcRepository.deleteAll();
        Assert.assertEquals(0,bookingJdbcRepository.size());

        //save
        bookingJdbcRepository.save(createBooking1());
        Assert.assertEquals(1,bookingJdbcRepository.size());

        //find all
        List<Booking> bookingsList = Lists.newArrayList(bookingJdbcRepository.findAll());

        //find one
        Booking booking = bookingJdbcRepository.findOne(bookingsList.get(0).getId());

        //test FINDE ONE
        Assert.assertEquals(1, booking.getFlightId());
        Assert.assertEquals("Test1", booking.getClientName());
        Assert.assertEquals("TestAddress1", booking.getClientAddress());
        Assert.assertEquals("testTourist1, testTourist2", booking.getTourists());
        Assert.assertEquals(2, booking.getNrSeats());


    }

    @Test
    public void testFindAllBooking() {
        //delete all rows from bookings
        bookingJdbcRepository.deleteAll();
        Assert.assertEquals(0,bookingJdbcRepository.size());

        //save
        bookingJdbcRepository.save(createBooking1());
        Assert.assertEquals(1,bookingJdbcRepository.size());

        //find all
        List<Booking> bookingsList = Lists.newArrayList(bookingJdbcRepository.findAll());

        //test FIND ALL
        Assert.assertEquals(1, bookingsList.size());
        Assert.assertEquals(1, bookingsList.get(0).getFlightId());
        Assert.assertEquals("Test1", bookingsList.get(0).getClientName());
        Assert.assertEquals("TestAddress1", bookingsList.get(0).getClientAddress());
        Assert.assertEquals("testTourist1, testTourist2", bookingsList.get(0).getTourists());
        Assert.assertEquals(2, bookingsList.get(0).getNrSeats());

    }



    private Booking createBooking1() {
        return Booking.builder().
                flightId(1).
                clientName("Test1").
                clientAddress("TestAddress1").
                tourists("testTourist1, testTourist2").
                nrSeats(2).
                build();
    }

    private Booking updateBooking2() {
        return Booking.builder().
                flightId(2).
                clientName("UpdateName").
                clientAddress("UpdateAddress").
                tourists("updateTourist").
                nrSeats(1).
                build();
    }

}
