package losszero.losszero.controller.realtime;

import losszero.losszero.dto.realtime.RealtimeProdDTO;
import losszero.losszero.service.realtime.RealtimeProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/realtime/prod")
public class RealtimeProductController {

    private final RealtimeProductService realtimeProductService;

    @PostMapping
    public ResponseEntity<?> saveProductData(
            @RequestParam int lineId, @RequestBody RealtimeProdDTO productData) {

        realtimeProductService.saveProductData(lineId, productData);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "데이터 전송에 성공했습니다."));
    }

    @GetMapping
    public SseEmitter streamRealtimeData(@RequestParam int lineId) {

        SseEmitter emitter = new SseEmitter();
        realtimeProductService.streamRealtimeData(lineId, emitter);
        return emitter;
    }
}

