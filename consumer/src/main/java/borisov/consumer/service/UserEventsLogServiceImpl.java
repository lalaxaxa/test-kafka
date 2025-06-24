package borisov.consumer.service;

import borisov.consumer.client.ExternalService;
import borisov.consumer.dto.UserEventsLogResponse;
import borisov.consumer.event.UserEvent;
import borisov.consumer.mapper.UserEventsLogMapper;
import borisov.consumer.model.UserEventsLog;
import borisov.consumer.repository.UserEventsLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserEventsLogServiceImpl implements UserEventsLogService {
    private final UserEventsLogRepository repository;
    private final UserEventsLogMapper mapper;
    private final ExternalService externalService;


    @Transactional
    public void save(UserEvent userEvent) {
        UserEventsLog log = mapper.toEntity(userEvent);
        repository.save(log);
        UserEventsLogResponse logSubmit = mapper.toResponseDTO(log);
        externalService.sentSubmitLogging(logSubmit);
    }
}
