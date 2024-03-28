package hei.shool.bank.entities;


import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankSoldHistory implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private BankSold value;

    private String bankSoldDate;
    @Override
    public void setId(Long id) {
      this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
