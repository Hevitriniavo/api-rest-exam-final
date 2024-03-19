package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.WithdrawalRequest;

import java.util.Map;

public interface WithdrawalService {

    Map<String, String> withdrawMoney(WithdrawalRequest withdrawalRequest);
}
