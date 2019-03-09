package persistence.repository;

import persistence.model.Booking;

public class BookingRepository extends AbstractRepository<Integer, Booking> {

    public BookingRepository(Validator<Booking> val) {
        super(val);

        save(new Booking(1, 1, "Both Tihamer","Cluj-Napoca str. Migdalului","tourist1, tourist2, tourist3", 5));
        save(new Booking(2, 2, "Groza Ioan", "Sibiu", "tourist1, tourist2, tourist3", 1));
        save(new Booking(3, 3, "Elek Hannelore","Zalau","tourist1, tourist2, tourist3", 2));
        save(new Booking(4, 1, "Oana Lung", "Targu-Mures","tourist1, tourist2, tourist3", 13));
        save(new Booking(5, 2, "Miron Andrada", "Brasov", "tourist1, tourist2, tourist3", 2));
    }
}
