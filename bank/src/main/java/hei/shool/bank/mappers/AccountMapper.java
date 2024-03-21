package hei.shool.bank.mappers;

import hei.shool.bank.dtos.requests.CreateAccountRequest;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.entites.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, CreateAccountRequest, AccountResponse> {
    @Override
    public AccountResponse fromEntity(Account entity) {
        return new AccountResponse(
                entity.getId(),
                entity.getPassword(),
                entity.getLastName(),
                entity.getFirstName(),
                entity.getEmail(),
                entity.getBirthday(),
                entity.getBalance(),
                entity.getNetMonthlySalary(),
                entity.getAccountNumber(),
                entity.getOverdraftLimit(),
                entity.isOverdraftEnabled(),
                entity.getCreationDate()
        );
    }

    @Override
    public Account fromDTO(CreateAccountRequest dto) {
        Account account = new Account();
        account.setPassword(dto.password());
        account.setLastName(dto.lastName());
        account.setFirstName(dto.firstName());
        account.setEmail(dto.email());
        account.setBirthday(dto.birthday());
        account.setBalance(dto.balance());
        account.setBankId(dto.bankId());
        account.setNetMonthlySalary(dto.netMonthlySalary());
        return account;
    }
}
