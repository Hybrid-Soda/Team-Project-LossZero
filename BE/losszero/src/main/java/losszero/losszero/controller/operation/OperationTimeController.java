package losszero.losszero.controller.operation;

import lombok.RequiredArgsConstructor;
import losszero.losszero.service.operation.OperationTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operation")
public class OperationTimeController {

    private final OperationTimeService operationTimeService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOperationTime(@RequestParam Long lineId, @RequestParam LocalDate date) {
        Duration operationTime = operationTimeService.getOperationTime(lineId, date);
        return ResponseEntity.ok(Map.of("operation_time", formatDuration(operationTime)));
    }

    // Duration 을 시, 분, 초로 포맷팅
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
