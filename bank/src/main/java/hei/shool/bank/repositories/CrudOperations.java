package hei.shool.bank.repositories;

import hei.shool.bank.helpers.Paginate;

import java.util.List;
import java.util.Optional;

public interface CrudOperations <T, ID>{
    List<T> findAll();

    T deleteById(ID id);

    Optional<T> findById(ID id);

    T saveOrUpdate(T toSave);
    Optional<T> findByField(String column, String value);

    Paginate<T> pagination(Long pageNumber, Long pageSize);

    Long getTotalRowCount();
}
