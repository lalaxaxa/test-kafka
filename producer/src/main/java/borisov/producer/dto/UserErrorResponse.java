package borisov.producer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserErrorResponse {
    private final String message;
    private final long timestamp;
}
