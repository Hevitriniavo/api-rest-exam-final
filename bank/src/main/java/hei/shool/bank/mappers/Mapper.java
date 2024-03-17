package hei.shool.bank.mappers;

public interface Mapper <T, Q, R>{

    R fromEntity(T entity);
    T fromDTO(Q dto);
}
