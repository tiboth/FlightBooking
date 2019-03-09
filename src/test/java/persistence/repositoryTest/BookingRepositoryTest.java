package persistence.repositoryTest;

import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;
import persistence.model.Booking;
import persistence.repository.BookingRepository;
import persistence.repository.BookingValidator;
import persistence.repository.Validator;

import java.util.List;

public class BookingRepositoryTest {

    private BookingRepository bookingRepository;

    public BookingRepositoryTest() {
        Validator<Booking> bookingValidator = new BookingValidator();
        bookingRepository = new BookingRepository(bookingValidator);
    }

    @Test
    public void testSizeBooking() {
        Assert.assertEquals(5, bookingRepository.size());
    }

    @Test
    public void testSaveBooking() {
        //test SAVE booking
        Assert.assertEquals(5, bookingRepository.size());
        bookingRepository.save(createBooking1());
        Assert.assertEquals(6, bookingRepository.size());

        //delete added booking
        bookingRepository.delete(6);
    }

    @Test
    public void testUpdateBooking() {
        //save booking
        bookingRepository.save(createBooking2());

        //update booking
        bookingRepository.update(7, createUpdateBooking1());
        //find booking
        Booking booking = bookingRepository.findOne(7);

        //test BOOKING
        Assert.assertEquals(2, booking.getFlightId());
        Assert.assertEquals("UpdateName", booking.getClientName());
        Assert.assertEquals("UpdateAddress", booking.getClientAddress());
        Assert.assertEquals("updated tourist1", booking.getTourists());
        Assert.assertEquals(1, booking.getNrSeats());

        //delete booking
        bookingRepository.delete(7);
    }

    @Test
    public void testDeleteBooking() {
        int initialSize = bookingRepository.size();

        //save booking
        Assert.assertEquals(initialSize, bookingRepository.size());
        bookingRepository.save(createBooking3());
        Assert.assertEquals(initialSize + 1, bookingRepository.size());

        //test DELETE booking
        bookingRepository.delete(createBooking3().getId());
        Assert.assertEquals(initialSize, bookingRepository.size());

    }

    @Test
    public void testFindOneBooking() {
        //save booking
        bookingRepository.save(createBooking3());

        //test BOOKING
        Booking booking = bookingRepository.findOne(8);

        Assert.assertEquals(2, booking.getFlightId());
        Assert.assertEquals("TestName", booking.getClientName());
        Assert.assertEquals("TestAddress", booking.getClientAddress());
        Assert.assertEquals("tourist1, tourist2", booking.getTourists());
        Assert.assertEquals(2, booking.getNrSeats());

        //delete booking
        bookingRepository.delete(8);
    }

    @Test
    public void testFindAllBooking() {
        int initialSize = bookingRepository.size();

        //test BOOKING
        Assert.assertEquals(initialSize, bookingRepository.size());
        List<Booking> bookingList = Lists.newArrayList(bookingRepository.findAll());
        Assert.assertEquals(initialSize, bookingList.size());
    }

    private Booking createBooking1() {
        return Booking.builder().
                id(6).
                flightId(1).
                clientName("TestName").
                clientAddress("TestAddress").
                tourists("tourist1, tourist2").
                nrSeats(2).
                build();
    }

    private Booking createBooking2() {
        return Booking.builder().
                id(7).
                flightId(2).
                clientName("TestName").
                clientAddress("TestAddress").
                tourists("tourist1, tourist2").
                nrSeats(2).
                build();
    }

    private Booking createBooking3() {
        return Booking.builder().
                id(8).
                flightId(2).
                clientName("TestName").
                clientAddress("TestAddress").
                tourists("tourist1, tourist2").
                nrSeats(2).
                build();
    }

    private Booking createUpdateBooking1() {
        return Booking.builder().
                id(7).
                flightId(2).
                clientName("UpdateName").
                clientAddress("UpdateAddress").
                tourists("updated tourist1").
                nrSeats(1).
                build();
    }
}
