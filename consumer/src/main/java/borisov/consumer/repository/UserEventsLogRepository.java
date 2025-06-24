package borisov.consumer.repository;

import borisov.consumer.model.UserEventsLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEventsLogRepository extends JpaRepository<UserEventsLog, Integer> {
}
