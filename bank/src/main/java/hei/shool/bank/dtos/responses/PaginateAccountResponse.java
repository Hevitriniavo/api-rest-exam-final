package hei.shool.bank.dtos.responses;

import java.util.List;

public record PaginateAccountResponse (
         Long count,

         Long next,

         Long previous,

         List<AccountResponse> items
){}
