package losszero.losszero.service.realtime;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.realtime.ProductDTO;
import losszero.losszero.dto.realtime.RealtimeProductDTO;
import losszero.losszero.dto.realtime.TodayRealtimeProductDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.entity.realtime.RealtimeProduct;
import losszero.losszero.repository.date.DateProdRepository;
import losszero.losszero.repository.realtime.RealtimeProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RealtimeProductService {

    private final RealtimeProductRepository realtimeProductRepository;
    private final DateProdRepository dateProductRepository;

    @Transactional
    public void saveProductData(RealtimeProductDTO productData) {
        saveRealtimeProduct(productData);
        updateDateProduct(productData);
    }

    private void saveRealtimeProduct(RealtimeProductDTO productData) {
        RealtimeProduct realtimeProduct = RealtimeProduct.builder()
                .lineId(productData.getLineId())
                .normal(productData.getQuality().getNormal())
                .defective(productData.getQuality().getDefective())
                .reusable(productData.getQuality().getReusable())
                .createdAt(LocalDateTime.now())
                .build();
        realtimeProductRepository.save(realtimeProduct);
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

    @Transactional(readOnly = true)
    public TodayRealtimeProductDTO getProductData(int lineId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        List<ProductDTO> products = realtimeProductRepository
                .findByLineIdAndCreatedAtBetween(lineId, startOfDay, endOfDay)
                .stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());

        TodayRealtimeProductDTO operationData = new TodayRealtimeProductDTO();
        operationData.setLineId(lineId);
        operationData.setProducts(products);

        return operationData;
    }

    private ProductDTO convertToProductDTO(RealtimeProduct realtimeProduct) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setNormal(realtimeProduct.getNormal());
        productDTO.setDefective(realtimeProduct.getDefective());
        productDTO.setReusable(realtimeProduct.getReusable());
        productDTO.setCreatedAt(realtimeProduct.getCreatedAt().toString());
        return productDTO;
    }
}