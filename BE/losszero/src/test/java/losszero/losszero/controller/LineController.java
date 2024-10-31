package losszero.losszero.controller;

import losszero.losszero.dto.line.LineStatusResponseDTO;
import losszero.losszero.service.line.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}