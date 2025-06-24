package borisov.consumer.client;

import borisov.consumer.dto.UserEventsLogResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "submit-logging")
public interface ExternalService {
    @PostMapping
    void sentSubmitLogging(@RequestBody UserEventsLogResponse userEventsLogResponse);
}
