package borisov.producer.service;

import borisov.producer.dto.UserEventsLoggingSubmit;
import borisov.producer.dto.UserResponse;
import borisov.producer.event.OperationType;
import borisov.producer.dto.UserCreateRequest;
import borisov.producer.event.UserEvent;
import borisov.producer.mapper.UserMapper;
import borisov.producer.model.User;
import borisov.producer.producer.UserEventProducer;
import borisov.producer.repository.UserRepository;
import borisov.producer.exception.UserEmailAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserEventProducer producer;

    @Transactional
    public UserResponse save(UserCreateRequest userCreateRequest){
        if (existsByEmail(userCreateRequest.getEmail(), null))
            throw new UserEmailAlreadyExistException();

        User savedUser = userMapper.toEntity(userCreateRequest);
        completeUserCreation(savedUser);
        userRepository.save(savedUser);
        producer.sendEvent(new UserEvent(savedUser.getId(), savedUser.getEmail(), OperationType.CREATE));
        return userMapper.toResponseDTO(savedUser);
    }

    public void submitLogging(UserEventsLoggingSubmit submit){
        log.info("Событие {} было добавлено в UserEventLog №{}", submit.getOperationType(), submit.getId());
    }

    private boolean existsByEmail(String email, Integer excludeId){
        if (excludeId == null) return userRepository.findByEmail(email).isPresent();
        else {
            Optional<User> user = userRepository.findByEmail(email);
            return user.filter(value -> value.getId() != excludeId).isPresent();
        }
    }

    private void completeUserCreation(User user) {
        user.setCreatedAt(OffsetDateTime.now(ZoneOffset.UTC));
    }

}
