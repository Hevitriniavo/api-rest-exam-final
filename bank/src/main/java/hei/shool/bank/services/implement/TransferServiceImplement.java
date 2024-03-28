package hei.shool.bank.services.implement;

import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.dtos.responses.TransferResponse;
import hei.shool.bank.services.TransferService;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImplement implements TransferService {
    @Override
    public TransferResponse transferTo(TransferRequest request) {
        return null;
    }
}
