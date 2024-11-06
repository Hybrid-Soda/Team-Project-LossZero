package losszero.losszero.controller.date;

import losszero.losszero.dto.date.CircumstanceSummaryDTO;
import losszero.losszero.dto.date.ProductionSummaryDTO;
import losszero.losszero.service.date.DateCircumstanceService;
import losszero.losszero.service.date.DateProdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/daily")
public class DateController {

    @Autowired
    private DateCircumstanceService dateCircumstanceService;

    @Autowired
    private DateProdService dateProdService;

    @GetMapping("/circumstance")
    public ResponseEntity<CircumstanceSummaryDTO> getCircumstanceSummary(
            @RequestParam("lineId") int lineId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<CircumstanceSummaryDTO> summary = dateCircumstanceService.getSummary(lineId, startDate, endDate);
        return ResponseEntity.ok(summary.get(0));
    }

    @GetMapping("/prod")
    public ResponseEntity<ProductionSummaryDTO> getProductionSummary(
            @RequestParam("lineId") int lineId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        List<ProductionSummaryDTO> summaryList = dateProdService.getSummary(lineId, startDate, endDate);
        return ResponseEntity.ok(summaryList.get(0));  // 첫 번째 값 반환
    }
}