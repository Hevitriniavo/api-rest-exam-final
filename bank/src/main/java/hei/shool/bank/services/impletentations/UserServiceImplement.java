package hei.shool.bank.services.impletentations;

import hei.shool.bank.dtos.requests.UserRequest;
import hei.shool.bank.dtos.responses.UserResponse;
import hei.shool.bank.entites.User;
import hei.shool.bank.mappers.UserMapper;
import hei.shool.bank.repositories.UserRepository;
import hei.shool.bank.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImplement(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponse saveOrUpdate(UserRequest user) {
        User savedUser = userRepository.saveOrUpdate(userMapper.fromDTO(user));
        return userMapper.fromEntity(savedUser);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository
                .findAll()
                .stream().map(userMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse deleteById(Long id) {
        User deletedUser = userRepository.deleteById(id);
        if (deletedUser != null) {
            return userMapper.fromEntity(deletedUser);
        } else {
            return null;
        }
    }

    @Override
    public UserResponse findById(Long id) {
        Optional<User> optionalAccount = userRepository.findById(id);
        return optionalAccount.map(userMapper::fromEntity).orElse(null);
    }
}
