package hei.shool.bank.services.implement;

import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.dtos.responses.TransferResponse;
import hei.shool.bank.entities.Account;
import hei.shool.bank.entities.Transfer;
import hei.shool.bank.enums.TransferStatus;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.TransferRepository;
import hei.shool.bank.services.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferServiceImplement implements TransferService {

    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;

    @Override
    public TransferResponse transferTo(TransferRequest request) {
        Account senderAccount = getSenderAccount(request.senderAccountNumber());
        Account receiverAccount = getReceiverAccount(request.receiverAccountNumber());

        BigDecimal amount = request.amount();
        BigDecimal senderBalance = senderAccount.getBalance();

        if (senderBalance.compareTo(amount) >= 0) {
            performTransfer(senderAccount, receiverAccount, amount, request.description());
            return generateSuccessResponse();
        } else {
            return generateFailureResponse();
        }
    }

    private Account getSenderAccount(String senderAccountNumber) {
        return accountRepository.findByField("accountNumber", senderAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Compte émetteur non trouvé"));
    }

    private Account getReceiverAccount(String receiverAccountNumber) {
        return accountRepository.findByField("accountNumber", receiverAccountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Compte destinataire non trouvé"));
    }

    private void performTransfer(Account senderAccount, Account receiverAccount, BigDecimal amount, String description) {
        BigDecimal newSenderBalance = senderAccount.getBalance().subtract(amount);
        BigDecimal newReceiverBalance = receiverAccount.getBalance().add(amount);

        senderAccount.setBalance(newSenderBalance);
        receiverAccount.setBalance(newReceiverBalance);

        saveTransfer(senderAccount.getId(), receiverAccount.getId(), amount, description);
        updateAccounts(senderAccount, receiverAccount);
    }

    private void saveTransfer(Long senderAccountId, Long receiverAccountId, BigDecimal amount, String description) {
        Transfer senderTransfer = buildTransfer(senderAccountId, receiverAccountId, amount, description);
        Transfer receiverTransfer = buildTransfer(senderAccountId, receiverAccountId, amount, description);

        transferRepository.saveOrUpdate(senderTransfer);
        transferRepository.saveOrUpdate(receiverTransfer);
    }

    private Transfer buildTransfer(Long senderAccountId, Long receiverAccountId, BigDecimal amount, String description) {
        return Transfer.builder()
                .senderAccountId(senderAccountId)
                .receiverAccountId(receiverAccountId)
                .amount(amount.doubleValue())
                .reason(description)
                .effectiveDate(LocalDateTime.now())
                .status(TransferStatus.COMPLETED)
                .build();
    }

    private void updateAccounts(Account senderAccount, Account receiverAccount) {
        accountRepository.saveOrUpdate(senderAccount);
        accountRepository.saveOrUpdate(receiverAccount);
    }

    private TransferResponse generateSuccessResponse() {
        return new TransferResponse(true, "Transfert réussi");
    }

    private TransferResponse generateFailureResponse() {
        return new TransferResponse(false, "Solde insuffisant pour effectuer le transfert");
    }
}
