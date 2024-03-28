package hei.shool.bank.services.implement;

import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.dtos.responses.TransferResponse;
import hei.shool.bank.entities.Account;
import hei.shool.bank.entities.Transaction;
import hei.shool.bank.enums.TransactionType;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.TransactionRepository;
import hei.shool.bank.services.TransferService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransferServiceImplement implements TransferService {

    private final AccountRepository accountRepository;

    private final TransactionRepository transactionRepository;

    public TransferServiceImplement(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }


    @Override
    public TransferResponse transfer(TransferRequest request) {
        Optional<Account> optionalSenderAccount = accountRepository.findByField("accountNumber",request.senderAccountNumber());
        Optional<Account> optionalReceiverAccount = accountRepository.findByField("accountNumber", request.receiverAccountNumber());
        if (optionalSenderAccount.isPresent() && optionalReceiverAccount.isPresent()) {
            Account senderAccount = optionalSenderAccount.get();
            Account receiverAccount = optionalReceiverAccount.get();

            if (senderAccount.getBalance() >= request.amount()) {

                senderAccount.debit(request.amount());
                Transaction senderTransaction = Transaction.builder()
                        .accountId(senderAccount.getId())
                        .amount(request.amount())
                        .type(TransactionType.TRANSFER_OUT)
                        .description(request.description())
                        .operationDate(LocalDateTime.now())
                        .build();

                receiverAccount.credit(request.amount());
                Transaction receiverTransaction = Transaction.builder()
                        .accountId(receiverAccount.getId())
                        .amount(request.amount())
                        .type(TransactionType.TRANSFER_IN)
                        .description(request.description())
                        .operationDate(LocalDateTime.now())
                        .build();

                transactionRepository.saveOrUpdate(senderTransaction);
                transactionRepository.saveOrUpdate(receiverTransaction);
                accountRepository.saveOrUpdate(senderAccount);
                accountRepository.saveOrUpdate(receiverAccount);
                return new TransferResponse(true, "Transfert réussi");
            } else {
                return new TransferResponse(false, "Solde insuffisant dans le compte de l'expéditeur");
            }
        } else {
            return new TransferResponse(false, "Compte de l'expéditeur ou du destinataire non trouvé");
        }
    }
}
