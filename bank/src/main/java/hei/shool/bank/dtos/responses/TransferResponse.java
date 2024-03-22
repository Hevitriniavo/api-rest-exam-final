package hei.shool.bank.dtos.responses;

import hei.shool.bank.enums.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransferResponse(
        Long id,
        Long senderAccountId,

        Long receiverAccountId,
        BigDecimal amount,

        String reason,

        LocalDate effectiveDate,

        LocalDateTime registrationDate,
        LocalDateTime reference,
        TransferStatus status
) {
}
