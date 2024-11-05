package losszero.losszero.controller.operation;

import losszero.losszero.dto.operation.OperationTimeDTO;
import losszero.losszero.service.operation.OperationTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/operation")
public class OperationTimeController {

    @Autowired
    private OperationTimeService operationTimeService;

    @PutMapping("/start")
    public ResponseEntity<Map<String, Object>> startOperation(@RequestParam Long lineId) {
        OperationTimeDTO operationTimeDTO = operationTimeService.startOperation(lineId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "공장 가동 시작하였습니다.",
                "lineId", operationTimeDTO.getLineId(),
                "cycleProdId", operationTimeDTO.getCycleProdId()
        ));
    }

    @PatchMapping("/end")
    public ResponseEntity<Map<String, Object>> endOperation(@RequestParam Long lineId, @RequestParam Long cycleProdId) {
        OperationTimeDTO operationTimeDTO = operationTimeService.endOperation(lineId, cycleProdId);
        return ResponseEntity.ok(Map.of(
                "message", "공장 가동 중지하였습니다.",
                "operationTime", formatDuration(operationTimeDTO.getOperationTime())
        ));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOperationTime(@RequestParam Long lineId, @RequestParam LocalDate date) {
        Duration operationTime = operationTimeService.getOperationTime(lineId, date);
        return ResponseEntity.ok(Map.of("operation_time", formatDuration(operationTime)));
    }

    // Duration을 시, 분, 초로 포맷팅
    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
