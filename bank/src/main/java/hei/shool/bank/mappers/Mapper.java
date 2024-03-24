package hei.shool.bank.mappers;

public interface Mapper<T, Q, R>{
    R toResponse(T entity);

    T toEntity(Q request);
}
