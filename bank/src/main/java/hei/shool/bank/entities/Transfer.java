package hei.shool.bank.entities;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.enums.TransferStatus;
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
public class Transfer implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private Long senderAccountId;

    private Long receiverAccountId;

    private Double amount;

    private String comment;

    private LocalDateTime effectiveDate;

    private Long bankId;

    private String reference;

    private TransferStatus status;
    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public Long getId() {
        return id;
    }
}
