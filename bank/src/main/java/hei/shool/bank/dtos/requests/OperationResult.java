package hei.shool.bank.dtos.requests;

public record OperationResult (
   boolean isSuccess,
   String  message
){ }
