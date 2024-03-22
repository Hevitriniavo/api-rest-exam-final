package hei.shool.bank.dtos.requests;

import hei.shool.bank.enums.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransferRequest(
         Long senderAccountId,

         Long receiverAccountId,

         BigDecimal amount,

         String reason,

         LocalDate effectiveDate,

         LocalDateTime registrationDate,

         TransferStatus status
) {}
