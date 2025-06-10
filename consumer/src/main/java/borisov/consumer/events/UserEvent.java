package borisov.consumer.events;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserEvent {
    private int id;
    private String email;
    private OperationType operationType;
}
