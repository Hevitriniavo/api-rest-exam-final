package hei.shool.bank.entites;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Account implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private Double balance;

    private Double netMonthlySalary;

    private String accountNumber;

    private Double overdraftLimit;

    private boolean overdraftEnabled;

    private LocalDate creationDate;

    private Long userId;

    public Account() {
        this.overdraftLimit = netMonthlySalary / 3;
        this.overdraftEnabled = false;
    }

    public Account(Double initialBalance, Double netMonthlySalary, Long userId) {
        this.balance = initialBalance;
        this.netMonthlySalary = netMonthlySalary;
        this.overdraftLimit = netMonthlySalary / 3;
        this.overdraftEnabled = false;
        this.userId = userId;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public Long getId() {
        return id;
    }

    public boolean credit(Double amount) {
        if (amount > 0){
            balance += amount;
            return true;
        }
        return false;
    }

    public boolean debit(Double amount) {
        if (balance >= amount || (overdraftEnabled && (balance + overdraftLimit) >= amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }
    public boolean transferTo(Account destinationAccount, Double amount) {
        if (debit(amount)) {
            return destinationAccount.credit(amount);
        }
        return false;
    }
}
