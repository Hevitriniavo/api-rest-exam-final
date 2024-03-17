package hei.shool.bank.mappers;

import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.entites.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, AccountRequest, AccountResponse> {
    @Override
    public AccountResponse fromEntity(Account entity) {
        return new AccountResponse(
                entity.getId(),
                entity.getBalance(),
                entity.getNetMonthlySalary(),
                entity.getAccountNumber(),
                entity.getOverdraftLimit(),
                entity.isOverdraftEnabled()
        );
    }

    @Override
    public Account fromDTO(AccountRequest dto) {
        Account account = new Account();
        account.setBalance(dto.balance());
        account.setNetMonthlySalary(dto.netMonthlySalary());
        account.setAccountNumber(dto.accountNumber());
        account.setOverdraftLimit(dto.overdraftLimit());
        account.setOverdraftEnabled(dto.overdraftEnabled());
        return account;
    }
}
