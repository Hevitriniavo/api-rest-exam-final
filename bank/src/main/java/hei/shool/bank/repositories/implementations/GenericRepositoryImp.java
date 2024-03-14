package hei.shool.bank.repositories.implementations;

import hei.shool.bank.config.DatabaseParam;
import hei.shool.bank.config.DatabaseSetting;
import hei.shool.bank.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
public class GenericRepositoryImp<T, ID> extends DatabaseSetting implements GenericRepository<T, ID>  {
    public GenericRepositoryImp(DatabaseParam config) {
        super(config);
    }

    @Override
    public T saveOrUpdate(T toSave) {
        Connection con = this.getConnection();
        return toSave;
    }

    @Override
    public T findById(ID id) {
        return null;
    }

    @Override
    public T deleteById(ID id) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return null;
    }

    @Override
    public List<T> findByField(String column, String value) {
        return null;
    }
}
