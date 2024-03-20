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
        account.setUserId(dto.userId());
        account.setId(dto.id());
        account.setBalance(dto.balance());
        account.setNetMonthlySalary(dto.netMonthlySalary());
        return account;
    }
}
