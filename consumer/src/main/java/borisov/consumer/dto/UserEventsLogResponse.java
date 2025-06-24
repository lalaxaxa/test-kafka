package borisov.consumer.dto;

import borisov.consumer.event.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEventsLogResponse {
    private int id;
    private OperationType operationType;
}
