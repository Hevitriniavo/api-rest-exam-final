package hei.shool.bank.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OverDraftResponse {
    private boolean success;
    private String message;
}
