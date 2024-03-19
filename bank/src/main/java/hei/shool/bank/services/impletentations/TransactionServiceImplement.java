package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.CreditOrDebitRequest;
import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.entites.Account;
import hei.shool.bank.entites.Transaction;
import hei.shool.bank.entites.Transfer;
import hei.shool.bank.enums.TransactionType;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.repositories.TransferRepository;
import hei.shool.bank.services.TransactionService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionServiceImplement implements TransactionService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;


    private final TransferRepository transferRepository;

    public TransactionServiceImplement(AccountRepository accountRepository, TransactionRepository transactionRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public boolean creditMoney(CreditOrDebitRequest creditOrDebitRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(creditOrDebitRequest.accountId());
        if (optionalAccount.isEmpty()) {
            return false;
        }
        Account account = optionalAccount.get();
        if (account.credit(creditOrDebitRequest.amount())) {
            Transaction transaction = new Transaction();
            transaction.setAccountId(creditOrDebitRequest.accountId());
            transaction.setAmount(creditOrDebitRequest.amount());
            transaction.setReason(creditOrDebitRequest.reason());
            transaction.setEffectiveDate(LocalDate.now());
            transaction.setRecordDate(LocalDateTime.now());
            transaction.setTransactionType("CREDIT");
            transactionRepository.saveOrUpdate(transaction);
            accountRepository.saveOrUpdate(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean debitMoney(CreditOrDebitRequest creditOrDebitRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(creditOrDebitRequest.accountId());
        if (optionalAccount.isEmpty()) {
            return false;
        }
        Account account = optionalAccount.get();
        if (account.debit(creditOrDebitRequest.amount())) {
            Transaction transaction = Transaction.builder()
                    .accountId(creditOrDebitRequest.accountId())
                    .amount(creditOrDebitRequest.amount())
                    .reason(creditOrDebitRequest.reason())
                    .effectiveDate(LocalDate.now())
                    .transactionType(TransactionType.DEBIT.name())
                    .recordDate(LocalDateTime.now())
                    .build();
            transactionRepository.saveOrUpdate(transaction);
            accountRepository.saveOrUpdate(account);
            return true;
        }
        return false;
    }

    @Override
    public boolean transferEnterTwoAccount(TransferRequest transferRequest) {
        Optional<Account> optionalSenderAccount = accountRepository.findById(transferRequest.senderAccountId());
        Optional<Account> optionalReceiverAccount = accountRepository.findById(transferRequest.receiverAccountId());
        if (optionalSenderAccount.isEmpty() || optionalReceiverAccount.isEmpty()) {
            return false;
        }
        Account senderAccount = optionalSenderAccount.get();
        Account receiverAccount = optionalReceiverAccount.get();
        if (senderAccount.transferTo(receiverAccount, transferRequest.amount())){
            Transfer transfer = Transfer.builder()
                    .amount(transferRequest.amount())
                    .senderAccountId(senderAccount.getId())
                    .receiverAccountId(receiverAccount.getId())
                    .reason(transferRequest.reason())
                    .build();
            transferRepository.saveOrUpdate(transfer);
            accountRepository.saveOrUpdate(senderAccount);
            accountRepository.saveOrUpdate(receiverAccount);
            return true;
        }
        return false;
    }
}
