package borisov.producer.mapper;

import borisov.producer.dto.UserCreateRequest;
import borisov.producer.dto.UserResponse;
import borisov.producer.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserResponse toResponseDTO(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    public User toEntity(UserResponse dto) {
        return modelMapper.map(dto, User.class);
    }

    public User toEntity(UserCreateRequest dto) {
        return modelMapper.map(dto, User.class);
    }
}
