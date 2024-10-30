package losszero.losszero.service.date;

import losszero.losszero.dto.date.CircumstanceSummaryDTO;
import losszero.losszero.entity.date.DateCircumstance;
import losszero.losszero.repository.date.DateCircumstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class DateCircumstanceService extends AbstractDateService<DateCircumstance, CircumstanceSummaryDTO> {

    @Autowired
    private DateCircumstanceRepository dateCircumstanceRepository;

    @Override
    public List<DateCircumstance> findEntitiesByLineAndDate(int lineId, LocalDate startDate, LocalDate endDate) {
        return dateCircumstanceRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);
    }

    @Override
    public List<CircumstanceSummaryDTO> createSummaryList(List<DateCircumstance> circumstances) {
        float maxTemp = Float.MIN_VALUE;
        float minTemp = Float.MAX_VALUE;
        float maxHumid = Float.MIN_VALUE;
        float minHumid = Float.MAX_VALUE;

        for (DateCircumstance c : circumstances) {
            maxTemp = Math.max(maxTemp, c.getMaxTemp());
            minTemp = Math.min(minTemp, c.getMinTemp());
            maxHumid = Math.max(maxHumid, c.getMaxHumid());
            minHumid = Math.min(minHumid, c.getMinHumid());
        }

        CircumstanceSummaryDTO dto = new CircumstanceSummaryDTO();
        dto.setMaxTemp(maxTemp);
        dto.setMinTemp(minTemp);
        dto.setMaxHumid(maxHumid);
        dto.setMinHumid(minHumid);

        return List.of(dto); // 단일 DTO를 리스트로 반환
    }
}