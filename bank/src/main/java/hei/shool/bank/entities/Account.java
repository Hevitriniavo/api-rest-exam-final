package hei.shool.bank.entities;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

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

    private Double balance;

    private Double netMonthlySalary;

    private Double overdraftLimit;

    private String accountNumber;

    private boolean overdraftEnabled;

    private LocalDate creationDate;

    private LocalDate lastWithdrawalDate;


    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void credit(Double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void debit(Double amount) {
        if (amount > 0) {
            balance -= amount;
        }
    }

    public void transfer(Account destinationAccount, Double amount) {
        if (balance >= amount) {
            debit(amount);
            destinationAccount.credit(amount);
        }
    }
}
