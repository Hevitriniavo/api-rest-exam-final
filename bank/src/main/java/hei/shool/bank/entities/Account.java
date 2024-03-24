package hei.shool.bank.entities;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private String password;

    private String lastName;

    private String firstName;

    private String email;

    private LocalDate birthday;

    private BigDecimal balance;

    private BigDecimal netMonthlySalary;

    private BigDecimal overdraftLimit;

    private String accountNumber;

    private boolean overdraftEnabled;

    private LocalDate creationDate;

    private LocalDate lastWithdrawalDate;

    private Long bankSoldId;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
