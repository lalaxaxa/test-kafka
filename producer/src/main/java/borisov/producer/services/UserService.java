package borisov.producer.services;

import borisov.producer.dto.CreateUserDTO;
import borisov.producer.dto.ResponseUserDTO;

public interface UserService {
    ResponseUserDTO save(CreateUserDTO createUserDTO);
}
