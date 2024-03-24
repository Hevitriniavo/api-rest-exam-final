package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.TransferRequest;
import hei.shool.bank.dtos.responses.TransferResponse;
import hei.shool.bank.services.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transferTo(@RequestBody TransferRequest request) {
        TransferResponse response = transferService.transferTo(request);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
