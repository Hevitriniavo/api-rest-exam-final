package hei.shool.bank.entites;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class User implements Identifiable<Long> {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String lastName;

    private String firstName;

    private String email;

    private LocalDate birthday;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    @Override
    public void setId(Long id) {
     this.id = id;
    }
    @Override
    public Long getId() {
        return id;
    }
}
