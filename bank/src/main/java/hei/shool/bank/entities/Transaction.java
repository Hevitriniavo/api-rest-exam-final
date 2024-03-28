package hei.shool.bank.entities;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.enums.TransactionType;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;

    private Double amount;

    private Long categoryId;

    private String description;

    private LocalDateTime operationDate;

    private TransactionType type;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
