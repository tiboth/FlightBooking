package persistence.model;

import lombok.*;
import persistence.repository.HasId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Booking implements HasId<Integer> {

    private Integer id;
    private int flightId;
    private String clientName;
    private String clientAddress;
    private String tourists;
    private int nrSeats;
}
