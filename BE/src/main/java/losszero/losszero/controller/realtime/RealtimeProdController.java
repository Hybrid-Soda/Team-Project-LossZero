package losszero.losszero.controller.realtime;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.realtime.TodayRealtimeProductDTO;
import losszero.losszero.service.realtime.RealtimeProductService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/realtime")
public class RealtimeProdController {

    private final RealtimeProductService realtimeProductService;

    @GetMapping("/prod")
    public ResponseEntity<Map<String, Object>> getTodayRealtimeProd(@RequestParam int lineId) {
        TodayRealtimeProductDTO todayRealtimeProductDTO = realtimeProductService.getProductData(lineId);
        System.out.println(todayRealtimeProductDTO);
        return ResponseEntity.ok(Map.of("data", todayRealtimeProductDTO));
    }
}
