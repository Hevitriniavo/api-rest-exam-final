package hei.shool.bank.repositories;

public interface Identifiable<ID> {
    void setId(ID id);

    ID getId();
}
