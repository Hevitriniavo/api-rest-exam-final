package hei.shool.bank.entites;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.time.LocalDateTime;

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

    private Double balance;

    private Double netMonthlySalary;

    private String accountNumber;

    private Double overdraftLimit;

    private boolean overdraftEnabled;

    private Long userId;

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
