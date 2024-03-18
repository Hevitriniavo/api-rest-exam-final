package hei.shool.bank.services;

import hei.shool.bank.dtos.requests.UserRequest;
import hei.shool.bank.dtos.responses.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveOrUpdate(UserRequest user);

    List<UserResponse> findAll();

    UserResponse deleteById(Long id);

    UserResponse findById(Long id);
}
