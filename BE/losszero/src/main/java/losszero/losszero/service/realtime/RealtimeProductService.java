package losszero.losszero.service.realtime;

import losszero.losszero.dto.realtime.RealtimeProductDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.entity.realtime.RealtimeProd;
import losszero.losszero.repository.date.DateProdRepository;
import losszero.losszero.repository.realtime.RealtimeProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RealtimeProductService {

    @Autowired
    private RealtimeProductRepository realtimeProductRepository;

    @Autowired
    private DateProdRepository dateProductRepository;

    public void saveProductData(RealtimeProductDTO productData) {
        int lineId = productData.getLineId();
        int normal = productData.getQuality().getNormal();
        int defective = productData.getQuality().getDefective();
        int reusable = productData.getQuality().getReusable();

        // 실시간 데이터를 realtime_prod 테이블에 저장
        RealtimeProd realtimeProd = new RealtimeProd();
        realtimeProd.setLineId(lineId);
        realtimeProd.setNormal(normal);
        realtimeProd.setDefective(defective);
        realtimeProd.setReusable(reusable);
        realtimeProd.setCreatedAt(LocalDateTime.now());
        realtimeProductRepository.save(realtimeProd);

        // 현재 날짜를 기반으로 date_prod에 누적 데이터를 저장
        LocalDate currentDate = LocalDate.now();
        Optional<DateProd> optionalDateProd = dateProductRepository.findByLineIdAndDate(lineId, currentDate);

        DateProd dateProd;
        if (optionalDateProd.isPresent()) {
            // 기존 레코드가 있으면 누적 업데이트
            dateProd = optionalDateProd.get();
            dateProd.setSumNormal(dateProd.getSumNormal() + normal);
            dateProd.setSumDefective(dateProd.getSumDefective() + defective);
            dateProd.setSumReusable(dateProd.getSumReusable() + reusable);
        } else {
            // 없으면 새로운 레코드를 생성
            dateProd = new DateProd();
            dateProd.setLineId(lineId);
            dateProd.setDate(currentDate);
            dateProd.setSumNormal(normal);
            dateProd.setSumDefective(defective);
            dateProd.setSumReusable(reusable);
        }

        // 변경 사항 저장
        dateProductRepository.save(dateProd);
    }
}