package losszero.losszero.controller.line;

import losszero.losszero.dto.line.LineStatusResponseDTO;
import losszero.losszero.dto.line.SetTargetProductDTO;
import losszero.losszero.service.line.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/line")
public class LineController {

    @Autowired
    private LineService lineService;

    @GetMapping
    public ResponseEntity<LineStatusResponseDTO> getLineStatus(@RequestParam("lineId") int lineId) {
        LineStatusResponseDTO responseDTO = lineService.getLineStatus(lineId);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/target")
    public ResponseEntity<?> setTargetProduct(@RequestParam("lineId") int lineId, @RequestBody SetTargetProductDTO dto) {
        lineService.setTargetProduct(lineId, dto);
        return ResponseEntity.status(201).body("{\"message\": \"목표 생산량 설정하였습니다.\"}");
    }
}