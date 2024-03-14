package hei.shool.bank.repositories;


import java.util.List;

public interface GenericRepository <T, ID>{

    T saveOrUpdate(T toSave);


    T findById(ID id);


    T deleteById(ID id);

    List<T> findAll();

    List<T> findByField(String column, String value);
}
