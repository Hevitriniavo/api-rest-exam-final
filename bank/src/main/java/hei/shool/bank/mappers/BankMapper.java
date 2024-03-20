package hei.shool.bank.mappers;

import hei.shool.bank.dtos.requests.BankRequest;
import hei.shool.bank.dtos.responses.BankResponse;
import hei.shool.bank.entites.Bank;
import org.springframework.stereotype.Component;

@Component
public class BankMapper implements Mapper<Bank, BankRequest, BankResponse> {
    @Override
    public BankResponse fromEntity(Bank entity) {
        return new BankResponse(
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getEmail()
        );
    }

    @Override
    public Bank fromDTO(BankRequest dto) {
        return Bank.builder()
                .id(dto.id())
                .name(dto.name())
                .phone(dto.phone())
                .address(dto.address())
                .email(dto.email())
                .build();
    }
}
