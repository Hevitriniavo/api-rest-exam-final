package hei.shool.bank.entites;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    private Long userId;


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
