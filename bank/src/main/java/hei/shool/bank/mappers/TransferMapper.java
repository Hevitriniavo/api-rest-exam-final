package hei.shool.bank.mappers;

import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.dtos.responses.TransferResponse;
import hei.shool.bank.entites.Transfer;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper implements Mapper<Transfer, TransferRequest, TransferResponse> {
    @Override
    public TransferResponse fromEntity(Transfer entity) {
        return null;
    }

    @Override
    public Transfer fromDTO(TransferRequest dto) {
        return null;
    }
}
