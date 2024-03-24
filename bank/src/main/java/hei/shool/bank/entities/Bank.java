package hei.shool.bank.entities;

import hei.shool.bank.repositories.Identifiable;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bank implements Identifiable<Long> {

    private Long id;

    private String name;

    private String code;

    private String email;
    @Override
    public void setId(Long id) {
       this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }
}
