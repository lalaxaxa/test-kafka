package borisov.consumer.service;


import event.UserEvent;

public interface UserEventsLogService {
    void save(UserEvent userEvent);
}
