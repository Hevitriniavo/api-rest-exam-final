package hei.shool.bank.entities;

import hei.shool.bank.annotations.GeneratedValue;
import hei.shool.bank.annotations.Id;
import hei.shool.bank.enums.CategoryName;
import hei.shool.bank.enums.CategoryType;
import hei.shool.bank.repositories.Identifiable;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category implements Identifiable<Long> {

    @Id
    @GeneratedValue
    private Long id;

    private CategoryName name;

    private CategoryType type;

    @Override
    public void setId(Long id) {
       this.id = id;
    }
    @Override
    public Long getId() {
        return id;
    }
}
