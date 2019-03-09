package persistence.model;

import lombok.*;
import persistence.repository.HasId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Employee implements HasId<Integer> {

    private Integer id;
    private String username;
    private String password;
}
