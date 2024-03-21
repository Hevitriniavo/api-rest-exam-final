package hei.shool.bank.services;

import hei.shool.bank.dtos.responses.BalanceResponse;

import java.time.LocalDate;

public interface BalanceService {
    BalanceResponse getConsultBalance(Long accountId, LocalDate selectedDate);
}
