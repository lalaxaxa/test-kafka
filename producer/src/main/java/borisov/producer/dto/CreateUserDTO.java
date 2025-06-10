package borisov.producer.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private String name;
    private String email;
    private Integer age;
}
