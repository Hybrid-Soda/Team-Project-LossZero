package losszero.losszero.controller.realtime;

import losszero.losszero.dto.realtime.RealtimeCircumstanceDTO;
import losszero.losszero.service.realtime.RealtimeCircumstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/realtime/circumstance")
public class RealtimeCircumstanceController {

    @Autowired
    private RealtimeCircumstanceService realtimeCircumstanceService;

    @PostMapping
    public ResponseEntity<?> saveCircumstanceData(@RequestParam Integer lineId, @RequestBody RealtimeCircumstanceDTO data) {
        if (lineId == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "lineId는 필수 파라미터입니다."));
        }
        realtimeCircumstanceService.saveCircumstanceData(lineId, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "데이터 전송에 성공했습니다."));
    }

    @GetMapping
    public SseEmitter streamRealtimeData(@RequestParam Integer lineId) {
        if (lineId == null) {
            throw new IllegalArgumentException("lineId는 필수 파라미터입니다.");
        }
        SseEmitter emitter = new SseEmitter();
        realtimeCircumstanceService.streamRealtimeData(lineId, emitter);
        return emitter;
    }
}
