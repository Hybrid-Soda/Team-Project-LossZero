package losszero.losszero.service.weekly;

import losszero.losszero.dto.weekly.WeeklyCircumstanceDTO;
import losszero.losszero.entity.date.DateCircumstance;
import losszero.losszero.repository.date.DateCircumstanceRepository;
import losszero.losszero.service.date.AbstractDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeeklyCircumstanceService extends AbstractDateService<DateCircumstance, WeeklyCircumstanceDTO> {

    @Autowired
    private DateCircumstanceRepository dateCircumstanceRepository;

    @Override
    public List<DateCircumstance> findEntitiesByLineAndDate(int lineId, LocalDate startDate, LocalDate endDate) {
        return dateCircumstanceRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);
    }

    @Override
    public List<WeeklyCircumstanceDTO> createSummaryList(List<DateCircumstance> circumstanceList) {
        return circumstanceList.stream().map(circumstance -> {
            WeeklyCircumstanceDTO dto = new WeeklyCircumstanceDTO();
            // Date를 LocalDate로 변환
            LocalDate date = circumstance.getDate();
            dto.setDate(date);
            dto.setMaxTemp(circumstance.getMaxTemp());
            dto.setMinTemp(circumstance.getMinTemp());
            dto.setMaxHumid(circumstance.getMaxHumid());
            dto.setMinHumid(circumstance.getMinHumid());
            return dto;
        }).collect(Collectors.toList());
    }

    // lineId만 받는 메서드 추가
    public List<WeeklyCircumstanceDTO> getSummary(int lineId) {
        List<DateCircumstance> circumstanceList = dateCircumstanceRepository.findTop7ByLineIdOrderByDateDesc(lineId);
        if (circumstanceList.isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }
        LocalDate startDate = circumstanceList.get(circumstanceList.size() - 1).getDate();
        LocalDate endDate = circumstanceList.get(0).getDate();

        return getSummary(lineId, startDate, endDate);
    }
}