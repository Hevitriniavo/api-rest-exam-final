package hei.shool.bank.services.implementations;

import hei.shool.bank.entities.User;
import hei.shool.bank.repositories.GenericRepository;
import hei.shool.bank.services.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final GenericRepository<User, Long> repository;

    public UserServiceImpl(GenericRepository<User, Long> repository) {
        this.repository = repository;
    }

    @Override
    public User saveOrUpdate(User user) {
        return this.repository.saveOrUpdate(user);
    }
}
