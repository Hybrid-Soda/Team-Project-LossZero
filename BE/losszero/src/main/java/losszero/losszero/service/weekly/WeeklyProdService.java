package losszero.losszero.service.weekly;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.weekly.WeeklyProductionDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.repository.date.DateProdRepository;
import losszero.losszero.service.date.AbstractDateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyProdService extends AbstractDateService<DateProd, WeeklyProductionDTO> {

    private final DateProdRepository dateProdRepository;

    @Override
    public List<DateProd> findEntitiesByLineAndDate(int lineId, LocalDate startDate, LocalDate endDate) {
        return dateProdRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);
    }

    @Override
    public List<WeeklyProductionDTO> createSummaryList(List<DateProd> prodList) {
        return prodList.stream()
                .map(prod -> WeeklyProductionDTO.builder()
                        .date(prod.getDate())
                        .sumNormal(prod.getSumNormal())
                        .sumDefective(prod.getSumDefective())
                        .sumReusable(prod.getSumReusable())
                        .total(prod.getSumNormal() + prod.getSumDefective() + prod.getSumReusable())
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<WeeklyProductionDTO> getSummary(int lineId) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);

        List<DateProd> prodList
                = dateProdRepository.findByLineIdAndDateBetweenOrderByDateDesc(lineId, startDate, endDate);

        // 데이터가 없으면 빈 리스트 반환으로 수정
        if (prodList.isEmpty()) {
            return List.of();
        }

        return createSummaryList(prodList);
    }
}