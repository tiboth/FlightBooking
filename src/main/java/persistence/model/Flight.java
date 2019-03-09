package persistence.model;

import lombok.*;
import persistence.repository.HasId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Flight implements HasId<Integer> {
    private Integer id;
    private String departureDate;
    private String departureTime;
    private String airport;
    private String destination;
    private int places;
}
