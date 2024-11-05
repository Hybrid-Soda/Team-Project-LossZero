package losszero.losszero.controller.operation;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.operation.OperationTimeDTO;
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
    private final OperationTimeDTO operationTimeDTO;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOperationTime(@RequestParam Long lineId, @RequestParam LocalDate date) {
        Duration operationTime = operationTimeService.getOperationTime(lineId, date);
        return ResponseEntity.ok(Map.of("operation_time", operationTimeDTO.formatDuration(operationTime)));
    }
}
