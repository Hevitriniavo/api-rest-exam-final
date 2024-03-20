package hei.shool.bank.entites;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.enums.TransferStatus;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Transfer implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private Long senderAccountId;

    private Long receiverAccountId;

    private BigDecimal amount;

    private String reason;

    private LocalDate effectiveDate;

    private LocalDateTime registrationDate;

    private TransferStatus status;

    private String reference;

    private LocalDateTime cancelledDate;


    @Override
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public Long getId() {
        return id;
    }
}
