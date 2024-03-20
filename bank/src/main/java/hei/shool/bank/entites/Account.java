package hei.shool.bank.entites;

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
@Builder
@NoArgsConstructor
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

    private String accountNumber;

    private BigDecimal overdraftLimit;

    private boolean overdraftEnabled;

    private LocalDate creationDate;

    private Long bankId;

    private LocalDate lastWithdrawalDate;

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public Long getId() {
        return id;
    }
}
