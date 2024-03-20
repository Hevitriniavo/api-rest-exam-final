package hei.shool.bank.dtos.requests;

import java.math.BigDecimal;

public record TransferRequest (
         Long senderAccountId,

         Long receiverAccountId,

         BigDecimal amount,

         String reason
){}
