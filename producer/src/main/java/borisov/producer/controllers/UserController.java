package borisov.producer.controllers;

import borisov.producer.dto.CreateUserDTO;
import borisov.producer.dto.ResponseUserDTO;
import borisov.producer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> create(@RequestBody CreateUserDTO createUserDTO){
        ResponseUserDTO saveUser = userService.save(createUserDTO);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }
}
