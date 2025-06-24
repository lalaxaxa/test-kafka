package borisov.producer.service;

import borisov.producer.dto.UserCreateRequest;
import borisov.producer.dto.UserEventsLoggingSubmit;
import borisov.producer.dto.UserResponse;

public interface UserService {
    UserResponse save(UserCreateRequest userCreateRequest);
    void submitLogging(UserEventsLoggingSubmit submit);
}
