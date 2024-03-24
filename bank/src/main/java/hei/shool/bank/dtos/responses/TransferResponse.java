package hei.shool.bank.dtos.responses;

public record TransferResponse(
        boolean isSuccess,
        String message
){}
