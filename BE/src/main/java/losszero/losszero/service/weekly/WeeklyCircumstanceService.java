package losszero.losszero.service.weekly;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.weekly.WeeklyCircumstanceDTO;
import losszero.losszero.entity.date.DateCircumstance;
import losszero.losszero.repository.date.DateCircumstanceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyCircumstanceService{

    private final DateCircumstanceRepository dateCircumstanceRepository;

    public List<WeeklyCircumstanceDTO> getSummary(int lineId) {
        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDate startDate = endDate.minusDays(6);

        List<DateCircumstance> circumstanceList = dateCircumstanceRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);

        // 데이터가 없으면 빈 리스트 반환
        if (circumstanceList.isEmpty()) {
            return List.of();
        }

        return createSummaryList(circumstanceList);
    }

    private List<WeeklyCircumstanceDTO> createSummaryList(List<DateCircumstance> circumstanceList) {
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
}