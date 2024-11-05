package losszero.losszero.service.realtime;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.realtime.RealtimeProductDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.entity.realtime.RealtimeProd;
import losszero.losszero.repository.date.DateProdRepository;
import losszero.losszero.repository.realtime.RealtimeProductRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RealtimeProductService {

    private final RealtimeProductRepository realtimeProductRepository;
    private final DateProdRepository dateProductRepository;

    public void saveProductData(RealtimeProductDTO productData) {
        saveRealtimeProduct(productData);
        updateDateProduct(productData);
    }

    private void saveRealtimeProduct(RealtimeProductDTO productData) {
        RealtimeProd realtimeProd = RealtimeProd.builder()
                .lineId(productData.getLineId())
                .normal(productData.getQuality().getNormal())
                .defective(productData.getQuality().getDefective())
                .reusable(productData.getQuality().getReusable())
                .createdAt(LocalDateTime.now())
                .build();
        realtimeProductRepository.save(realtimeProd);
    }

    private void updateDateProduct(RealtimeProductDTO productData) {
        int lineId = productData.getLineId();
        int normal = productData.getQuality().getNormal();
        int defective = productData.getQuality().getDefective();
        int reusable = productData.getQuality().getReusable();

        LocalDate currentDate = LocalDate.now();
        DateProd dateProd = dateProductRepository.findByLineIdAndDate(lineId, currentDate)
                .map(existingProd -> updateExistingDateProd(existingProd, normal, defective, reusable))
                .orElseGet(() -> createNewDateProd(lineId, currentDate, normal, defective, reusable));

        dateProductRepository.save(dateProd);
    }

    private DateProd updateExistingDateProd(DateProd dateProd, int normal, int defective, int reusable) {
        dateProd.setSumNormal(dateProd.getSumNormal() + normal);
        dateProd.setSumDefective(dateProd.getSumDefective() + defective);
        dateProd.setSumReusable(dateProd.getSumReusable() + reusable);
        return dateProd;
    }

    private DateProd createNewDateProd(int lineId, LocalDate date, int normal, int defective, int reusable) {
        return DateProd.builder()
                .lineId(lineId)
                .date(date)
                .sumNormal(normal)
                .sumDefective(defective)
                .sumReusable(reusable)
                .build();
    }
}