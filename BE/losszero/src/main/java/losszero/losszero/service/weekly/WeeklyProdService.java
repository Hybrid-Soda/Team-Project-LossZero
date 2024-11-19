package losszero.losszero.service.weekly;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.weekly.WeeklyProductionDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.repository.date.DateProdRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeeklyProdService {

    private final DateProdRepository dateProdRepository;

    public List<WeeklyProductionDTO> getSummary(int lineId) {
        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDate startDate = endDate.minusDays(6);

        List<DateProd> prodList = dateProdRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);

        if (prodList.isEmpty()) {
            return List.of();
        }

        return createSummaryList(prodList);
    }

    private List<WeeklyProductionDTO> createSummaryList(List<DateProd> prodList) {
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
}