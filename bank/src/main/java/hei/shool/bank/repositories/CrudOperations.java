package hei.shool.bank.repositories;

import java.util.List;

public interface CrudOperations <T, ID>{
    List<T> findAll();

    T deleteById(ID id);

    T findById(ID id);

    T saveOrUpdate(T toSave);
    List<T> findByField(String column, String value);
}
