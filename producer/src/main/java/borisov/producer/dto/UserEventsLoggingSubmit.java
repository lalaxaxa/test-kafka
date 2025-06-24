package borisov.producer.dto;


import borisov.producer.event.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEventsLoggingSubmit {
    private int id;
    private OperationType operationType;
}
