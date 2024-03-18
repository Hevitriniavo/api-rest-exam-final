package hei.shool.bank.controllers;

import hei.shool.bank.dtos.requests.UserRequest;
import hei.shool.bank.dtos.responses.UserResponse;
import hei.shool.bank.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        return this.userService.findAll();
    }

    @PostMapping("/create")
    public UserResponse createAccount(@RequestBody UserRequest user){
        return this.userService.saveOrUpdate(user);
    }

    @GetMapping("/{id}")
    public UserResponse getAccount(@PathVariable Long id){
        return this.userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public UserResponse destroyAccount(@PathVariable Long id){
        return this.userService.deleteById(id);
    }
}
