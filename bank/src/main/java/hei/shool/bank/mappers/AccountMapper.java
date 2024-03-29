package hei.shool.bank.mappers;

import hei.shool.bank.dtos.requests.AccountRequest;
import hei.shool.bank.dtos.responses.AccountResponse;
import hei.shool.bank.entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, AccountRequest, AccountResponse> {
    @Override
    public AccountResponse toResponse(Account entity) {
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
                entity.isOverdraftEnabled(),
                entity.getOverdraftLimit(),
                entity.getCreationDate(),
                entity.getLastWithdrawalDate()
        );
    }

    @Override
    public Account toEntity(AccountRequest request) {
        Double overdraftLimit = request.netMonthlySalary() != null ?request.netMonthlySalary() / 3 : 00.00;
        return Account.builder()
                .id(request.id())
                .password(request.password())
                .lastName(request.lastName())
                .firstName(request.firstName())
                .email(request.email())
                .birthday(request.birthday())
                .balance(00.00)
                .netMonthlySalary(request.netMonthlySalary())
                .overdraftLimit(overdraftLimit)
                .overdraftEnabled(false)
                .build();
    }
}
