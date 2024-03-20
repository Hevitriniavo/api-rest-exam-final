package hei.shool.bank.entites;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.enums.TransactionType;
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
@Builder
public class TransactionsHistory  implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private Long account;

    private Double amount;

    private String reason;

    private LocalDate effectiveDate;

    private LocalDateTime recordDate;

    private TransactionType transactionType;

    private Long categoryId;

    private String operationType;

    private LocalDateTime operationDate;


    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public Long getId() {
        return id;
    }
}
