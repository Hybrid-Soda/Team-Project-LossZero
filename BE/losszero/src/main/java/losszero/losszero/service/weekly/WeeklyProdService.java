package losszero.losszero.service.weekly;

import losszero.losszero.dto.weekly.WeeklyProductionDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.repository.date.DateProdRepository;
import losszero.losszero.service.date.AbstractDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeeklyProdService extends AbstractDateService<DateProd, WeeklyProductionDTO> {

    @Autowired
    private DateProdRepository dateProdRepository;

    @Override
    public List<DateProd> findEntitiesByLineAndDate(int lineId, LocalDate startDate, LocalDate endDate) {
        return dateProdRepository.findByLineIdAndDateBetween(lineId, startDate, endDate);
    }

    @Override
    public List<WeeklyProductionDTO> createSummaryList(List<DateProd> prodList) {
        return prodList.stream().map(prod -> {
            WeeklyProductionDTO dto = new WeeklyProductionDTO();
            // LocalDate로 변환하여 날짜를 정확히 처리
            LocalDate date = prod.getDate(); // 수정된 부분: getCreatedAt() -> getDate()
            dto.setDate(date);
            dto.setSumNormal(prod.getSumNormal());
            dto.setSumDefective(prod.getSumDefective());
            dto.setSumReusable(prod.getSumReusable());
            dto.setTotal(prod.getSumNormal() + prod.getSumDefective() + prod.getSumReusable());
            return dto;
        }).collect(Collectors.toList());
    }

    // lineId만 받는 메서드 오버로드
    public List<WeeklyProductionDTO> getSummary(int lineId) {
        List<DateProd> prodList = dateProdRepository.findTop7ByLineIdOrderByDateDesc(lineId);
        if (prodList.isEmpty()) {
            throw new IllegalArgumentException("데이터가 없습니다.");
        }
        LocalDate startDate = prodList.get(prodList.size() - 1).getDate(); // 수정된 부분: getCreatedAt() -> getDate()
        LocalDate endDate = prodList.get(0).getDate(); // 수정된 부분: getCreatedAt() -> getDate()

        return getSummary(lineId, startDate, endDate);
    }
}