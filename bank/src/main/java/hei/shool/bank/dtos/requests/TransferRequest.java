package hei.shool.bank.dtos.requests;

import java.math.BigDecimal;

public record TransferRequest (
        String senderAccountNumber,
        String receiverAccountNumber,

        BigDecimal amount,

        String description
){}
