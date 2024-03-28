package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.dtos.responses.TransferResponse;

public interface TransferService {
     TransferResponse transfer(TransferRequest request);
}
