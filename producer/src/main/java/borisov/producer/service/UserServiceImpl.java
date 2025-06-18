package borisov.producer.service;

import borisov.producer.dto.ResponseUserDTO;
import borisov.producer.event.OperationType;
import borisov.producer.dto.CreateUserDTO;
import borisov.producer.event.UserEvent;
import borisov.producer.producer.UserEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserEventProducer producer;

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
