package hei.shool.bank.entites;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Account implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private String customerFirstname;

    private String customerLastname;

    private LocalDate birthday;

    private Double netMonthlySalary;

    private Long accountNumber;
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
