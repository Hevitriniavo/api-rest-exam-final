package hei.shool.bank.entities;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.enums.TransferStatus;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

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

    private Long bankId;

    private Double amount;

    private String reason;

    private String effectiveDate;

    private TransferStatus status;

    private String reference;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
