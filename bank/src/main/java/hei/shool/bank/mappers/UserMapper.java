package hei.shool.bank.mappers;

import hei.shool.bank.dtos.requests.UserRequest;
import hei.shool.bank.dtos.responses.UserResponse;
import hei.shool.bank.entites.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserRequest, UserResponse> {
    @Override
    public UserResponse fromEntity(User entity) {
        return new UserResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getLastName(),
                entity.getFirstName(),
                entity.getEmail(),
                entity.getBirthday()
        );
    }

    @Override
    public User fromDTO(UserRequest dto) {
        User user = new User();
        user.setId(dto.id());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        user.setEmail(dto.email());
        user.setLastName(dto.lastName());
        user.setFirstName(dto.firstName());
        user.setBirthday(dto.birthday());
        return user;
    }
}
