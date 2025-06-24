package borisov.consumer.mapper;

import borisov.consumer.dto.UserEventsLogResponse;
import borisov.consumer.event.UserEvent;
import borisov.consumer.model.UserEventsLog;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventsLogMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void setup() {
        modelMapper.typeMap(UserEvent.class, UserEventsLog.class)
                .addMappings(mapper -> mapper.skip(UserEventsLog::setId)); // не мапим id
    }

    public UserEventsLogResponse toResponseDTO(UserEventsLog userEventLog) {
        return modelMapper.map(userEventLog, UserEventsLogResponse.class);
    }

    public UserEventsLog toEntity(UserEvent userEventDTO) {
        return modelMapper.map(userEventDTO, UserEventsLog.class);
    }

}
