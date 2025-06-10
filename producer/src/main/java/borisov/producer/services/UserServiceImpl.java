package borisov.producer.services;

import borisov.producer.dto.ResponseUserDTO;
import borisov.producer.events.OperationType;
import borisov.producer.dto.CreateUserDTO;
import borisov.producer.events.UserEvent;
import borisov.producer.producers.UserEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Slf4j
@Service

public class UserServiceImpl implements UserService{
    private final UserEventProducer producer;

    public UserServiceImpl(UserEventProducer producer) {
        this.producer = producer;
    }

    public ResponseUserDTO save(CreateUserDTO user){
        ResponseUserDTO savesUser = new ResponseUserDTO(
                new Random().nextInt(100),
                user.getName(),
                user.getEmail(),
                user.getAge()
        );
        producer.sendEvent(new UserEvent(savesUser.getId(), savesUser.getEmail(), OperationType.CREATE));
        return savesUser;
    }


}
