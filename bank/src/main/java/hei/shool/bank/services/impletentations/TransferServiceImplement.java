package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.dtos.responses.TransferResponse;
import hei.shool.bank.entites.Account;
import hei.shool.bank.entites.Transfer;
import hei.shool.bank.enums.TransferStatus;
import hei.shool.bank.mappers.TransferMapper;
import hei.shool.bank.repositories.AccountRepository;
import hei.shool.bank.repositories.TransferRepository;
import hei.shool.bank.services.TransferService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class TransferServiceImplement implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;

    private final TransferMapper transferMapper;

    public TransferServiceImplement(TransferRepository transferRepository, AccountRepository accountRepository, TransferMapper transferMapper) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
        this.transferMapper = transferMapper;
    }

    @Override
    public TransferResponse transferTo(TransferRequest request) {
        Optional<Account> optionalReceiverAccount = accountRepository.findById(request.receiverAccountId());
        Optional<Account> optionalSenderAccount = accountRepository.findById(request.senderAccountId());
        if (optionalSenderAccount.isEmpty() || optionalReceiverAccount.isEmpty()) {
            return null;
        }
        Account receiverAccount = optionalReceiverAccount.get();
        Account senderAccount = optionalSenderAccount.get();

        if (!Objects.equals(receiverAccount.getBankId(), senderAccount.getBankId())){
            return null;
        }
        if (senderAccount.getBalance().compareTo(BigDecimal.ZERO) >= 0 && senderAccount.getBalance().compareTo(request.amount()) >=0){
            BigDecimal senderAmount = senderAccount.getBalance();
            BigDecimal receiverAmount = receiverAccount.getBalance();
            senderAccount.setBalance(senderAmount.subtract(request.amount()));
            receiverAccount.setBalance(receiverAmount.add(request.amount()));
            Transfer transfer = Transfer.builder()
                    .receiverAccountId(receiverAccount.getId())
                    .reason(request.reason())
                    .senderAccountId(senderAccount.getId())
                    .effectiveDate(LocalDate.now())
                    .amount(request.amount())
                    .status(TransferStatus.COMPLETED)
                    .registrationDate(LocalDateTime.now())
                    .build();
           Transfer savedTransfer = transferRepository.saveOrUpdate(transfer);
            accountRepository.saveOrUpdate(receiverAccount);
            accountRepository.saveOrUpdate(senderAccount);
            return transferMapper.fromEntity(savedTransfer);
        }
        return null;
    }
}
