package borisov.consumer.service;

import borisov.consumer.event.UserEvent;

public interface UserEventsLogService {
    void save(UserEvent userEvent);
}
