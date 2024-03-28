package hei.shool.bank.dtos.responses;

public record OperationResponse(
  boolean isSuccess,
  String message
){}
