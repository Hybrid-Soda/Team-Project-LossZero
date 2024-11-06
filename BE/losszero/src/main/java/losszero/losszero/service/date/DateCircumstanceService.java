package losszero.losszero.service.date;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.date.CircumstanceSummaryDTO;
import losszero.losszero.entity.date.DateCircumstance;
import losszero.losszero.repository.date.DateCircumstanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateCircumstanceService {

    private final DateCircumstanceRepository dateCircumstanceRepository;

    public List<CircumstanceSummaryDTO> getSummary(int lineId, LocalDate startDate, LocalDate endDate) {
        List<DateCircumstance> circumstances = dateCircumstanceRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);

        if (circumstances.isEmpty()) {
            return List.of();
        }

        return createSummaryList(circumstances);
    }

    public List<CircumstanceSummaryDTO> createSummaryList(List<DateCircumstance> circumstances) {
        float maxTemp = Float.MIN_VALUE;
        float minTemp = Float.MAX_VALUE;
        float maxHumid = Float.MIN_VALUE;
        float minHumid = Float.MAX_VALUE;

        for (DateCircumstance circ : circumstances) {
            maxTemp = Math.max(maxTemp, circ.getMaxTemp());
            minTemp = Math.min(minTemp, circ.getMinTemp());
            maxHumid = Math.max(maxHumid, circ.getMaxHumid());
            minHumid = Math.min(minHumid, circ.getMinHumid());
        }

        CircumstanceSummaryDTO dto = new CircumstanceSummaryDTO();
        dto.setMaxTemp(maxTemp);
        dto.setMinTemp(minTemp);
        dto.setMaxHumid(maxHumid);
        dto.setMinHumid(minHumid);

        return List.of(dto);
    }
}