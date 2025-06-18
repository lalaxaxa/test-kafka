package borisov.producer.service;

import borisov.producer.dto.CreateUserDTO;
import borisov.producer.dto.ResponseUserDTO;

public interface UserService {
    ResponseUserDTO save(CreateUserDTO createUserDTO);
}
