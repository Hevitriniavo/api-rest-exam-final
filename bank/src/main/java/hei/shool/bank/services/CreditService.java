package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.CreditRequest;

import java.util.Map;

public interface CreditService {

    Map<String, String> withdrawMoney(CreditRequest creditRequest);
}
