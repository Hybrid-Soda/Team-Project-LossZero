package losszero.losszero.service.weekly;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.weekly.WeeklyCircumstanceDTO;
import losszero.losszero.entity.date.DateCircumstance;
import losszero.losszero.repository.date.DateCircumstanceRepository;
import losszero.losszero.service.date.AbstractDateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyCircumstanceService extends AbstractDateService<DateCircumstance, WeeklyCircumstanceDTO> {

    private final DateCircumstanceRepository dateCircumstanceRepository;

    @Override
    public List<DateCircumstance> findEntitiesByLineAndDate(int lineId, LocalDate startDate, LocalDate endDate) {
        return dateCircumstanceRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);
    }

    @Override
    public List<WeeklyCircumstanceDTO> createSummaryList(List<DateCircumstance> circumstanceList) {
        return circumstanceList.stream()
                .map(circ -> WeeklyCircumstanceDTO.builder()
                        .date(circ.getDate())
                        .maxTemp(circ.getMaxTemp())
                        .minTemp(circ.getMinTemp())
                        .maxHumid(circ.getMaxHumid())
                        .minHumid(circ.getMinHumid())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<WeeklyCircumstanceDTO> getSummary(int lineId) {
        List<DateCircumstance> circumstanceList = dateCircumstanceRepository.findTop7ByLineIdOrderByDateDesc(lineId);

        // 데이터가 없으면 빈 리스트 반환으로 수정
        if (circumstanceList.isEmpty()) {
            return List.of();
        }

        // 가장 오래된 날짜와 최신 날짜 가져오기
        LocalDate startDate = circumstanceList.get(circumstanceList.size() - 1).getDate();
        LocalDate endDate = circumstanceList.get(0).getDate();

        return getSummary(lineId, startDate, endDate);
    }
}