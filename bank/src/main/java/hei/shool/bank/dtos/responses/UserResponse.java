package hei.shool.bank.dtos.responses;

import java.time.LocalDate;

public record UserResponse (
        Long id,

        String username,

        String lastName,

        String firstName,

        String email,

        LocalDate birthday
) { }
