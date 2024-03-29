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
public class InterestHistory implements Identifiable<Long> {
    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;

    private Double amount;

    private Double interestRate;

    private LocalDate interestDate;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
