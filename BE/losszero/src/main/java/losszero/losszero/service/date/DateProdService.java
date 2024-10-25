package losszero.losszero.service.date;

import losszero.losszero.dto.date.ProductionSummaryDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.repository.date.DateProdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class DateProdService extends AbstractDateService<DateProd, ProductionSummaryDTO> {

    @Autowired
    private DateProdRepository dateProdRepository;

    @Override
    public List<DateProd> findEntitiesByLineAndDate(int lineId, LocalDate startDate, LocalDate endDate) {
        return dateProdRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);
    }

    @Override
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

        return List.of(dto); // 단일 객체를 리스트로 반환
    }
}