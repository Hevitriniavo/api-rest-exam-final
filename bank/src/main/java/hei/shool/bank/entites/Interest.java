package hei.shool.bank.entites;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Interest implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private Long accountId;

    private Double amount;

    private Double interestRate;

    private LocalDate interestDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
