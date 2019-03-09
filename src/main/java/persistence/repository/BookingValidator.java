package persistence.repository;

import persistence.model.Booking;

public class BookingValidator implements Validator<Booking> {

    @Override
    public void validate(Booking booking) throws ValidationException {
        StringBuffer msg=new StringBuffer();
        if (booking.getId()<0)
            msg.append("Id-ul nu poate fi negativ: " + booking.getId());
        if (booking.getId()<0)
            msg.append("Numarul de locuri nu poate fi negativ: " + booking.getNrSeats());
        if (msg.length() > 0)
            throw new ValidationException(msg.toString());
    }
}
