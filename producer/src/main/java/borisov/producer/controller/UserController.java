package borisov.producer.controller;

import borisov.producer.dto.UserCreateRequest;
import borisov.producer.dto.UserEventsLoggingSubmit;
import borisov.producer.dto.UserResponse;
import borisov.producer.dto.UserErrorResponse;
import borisov.producer.exception.UserEmailAlreadyExistException;
import borisov.producer.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest userCreateRequest){
        UserResponse saveUser = userService.save(userCreateRequest);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @PostMapping("/submit_logging")
    public ResponseEntity<Void> submitLogging(@RequestBody UserEventsLoggingSubmit submit){
        userService.submitLogging(submit);
        return ResponseEntity.ok().build();

    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserEmailAlreadyExistException e) {
        log.info(e.getMessage());
        UserErrorResponse response = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


}
