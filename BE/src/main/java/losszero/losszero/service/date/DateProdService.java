package losszero.losszero.service.date;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.date.ProductionSummaryDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.repository.date.DateProdRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DateProdService {

    private final DateProdRepository dateProdRepository;

    public List<ProductionSummaryDTO> getSummary(int lineId, LocalDate startDate, LocalDate endDate) {
        List<DateProd> products = dateProdRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);

        if (products.isEmpty()) {
            return List.of(new ProductionSummaryDTO(0, 0, 0, 0));
        }

        return createSummaryList(products);
    }

    public List<ProductionSummaryDTO> createSummaryList(List<DateProd> prodList) {
        long sumNormal = 0;
        long sumDefective = 0;
        long sumReusable = 0;

        for (DateProd prod : prodList) {
            sumNormal += prod.getSumNormal();
            sumDefective += prod.getSumDefective();
            sumReusable += prod.getSumReusable();
        }

        ProductionSummaryDTO dto = new ProductionSummaryDTO();
        dto.setSumNormal(sumNormal);
        dto.setSumDefective(sumDefective);
        dto.setSumReusable(sumReusable);
        dto.setTotal(sumNormal + sumDefective + sumReusable);

        return List.of(dto);
    }
}