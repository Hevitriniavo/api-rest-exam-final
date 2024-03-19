package hei.shool.bank.dtos.requests;



import java.time.LocalDate;

public record UserRequest (
         Long id,

         String username,

         String password,

         String lastName,

         String firstName,

         String email,

         LocalDate birthday
){}
