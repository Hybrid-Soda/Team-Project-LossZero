package losszero.losszero.controller.weekly;

import losszero.losszero.dto.weekly.WeeklyCircumstanceDTO;
import losszero.losszero.dto.weekly.WeeklyProductionDTO;
import losszero.losszero.service.weekly.WeeklyCircumstanceService;
import losszero.losszero.service.weekly.WeeklyProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weekly")
public class WeeklyController {

    @Autowired
    private WeeklyProdService weeklyProdService;

    @Autowired
    private WeeklyCircumstanceService weeklyCircumstanceService;

    @GetMapping("/prod")
    public ResponseEntity<List<WeeklyProductionDTO>> getWeeklyProduction(@RequestParam("lineId") int lineId) {
        List<WeeklyProductionDTO> summary = weeklyProdService.getSummary(lineId); // lineId만 전달
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/circumstance")
    public ResponseEntity<List<WeeklyCircumstanceDTO>> getWeeklyCircumstance(@RequestParam("lineId") int lineId) {
        List<WeeklyCircumstanceDTO> summary = weeklyCircumstanceService.getSummary(lineId);
        return ResponseEntity.ok(summary);
    }
}
