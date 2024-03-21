package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.responses.BalanceResponse;
import hei.shool.bank.repositories.BalanceHistoryRepository;
import hei.shool.bank.services.BalanceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class BalanceServiceImplement implements BalanceService {
    private final BalanceHistoryRepository balanceHistoryRepository;

    public BalanceServiceImplement(BalanceHistoryRepository balanceHistoryRepository) {
        this.balanceHistoryRepository = balanceHistoryRepository;
    }

    @Override
    public BalanceResponse getConsultBalance(Long accountId, LocalDate selectedDate) {
        Map<String, BigDecimal> loansBalanceAndInterest = balanceHistoryRepository.getLoansBalanceAndInterest(accountId, selectedDate);
        BigDecimal principalBalance = balanceHistoryRepository.getPrincipalBalance(accountId, selectedDate);
        BigDecimal loansBalance = loansBalanceAndInterest.getOrDefault("loansBalance", BigDecimal.ZERO);
        BigDecimal loansInterest = loansBalanceAndInterest.getOrDefault("loansInterest", BigDecimal.ZERO);
        return new BalanceResponse(principalBalance, loansBalance, loansInterest);
    }

}
