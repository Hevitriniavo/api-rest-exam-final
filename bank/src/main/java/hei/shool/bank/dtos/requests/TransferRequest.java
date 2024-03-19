package hei.shool.bank.dtos.requests;

public record TransferRequest (
         Long senderAccountId,

         Long receiverAccountId,

         Double amount,

         String reason
){}
