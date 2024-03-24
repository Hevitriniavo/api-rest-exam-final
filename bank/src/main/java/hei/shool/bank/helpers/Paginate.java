package hei.shool.bank.helpers;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paginate <T> {

    private Long count;

    private Long next;

    private Long previous;

    private List<T> items = new ArrayList<>();
}
