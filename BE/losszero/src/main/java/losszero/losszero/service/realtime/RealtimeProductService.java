package losszero.losszero.service.realtime;

import jakarta.transaction.Transactional;
import losszero.losszero.dto.realtime.RealtimeProdDTO;
import losszero.losszero.entity.date.DateProd;
import losszero.losszero.entity.realtime.RealtimeProd;
import losszero.losszero.repository.date.DateProdRepository;
import losszero.losszero.repository.realtime.RealtimeProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class RealtimeProductService {

    @Autowired
    private RealtimeProductRepository realtimeProductRepository;

    @Autowired
    private DateProdRepository dateProductRepository; // date_prod 테이블과 연동되는 리포지토리

    public void saveProductData(int lineId, RealtimeProdDTO productData) {
        // 실시간 데이터를 realtime_prod 테이블에 저장
        RealtimeProd realtimeProd = new RealtimeProd();
        realtimeProd.setLineId(lineId);
        realtimeProd.setNormal(productData.getNormal());
        realtimeProd.setDefective(productData.getDefective());
        realtimeProd.setReusable(productData.getReusable());
        realtimeProd.setCreatedAt(LocalDateTime.now());
        realtimeProductRepository.save(realtimeProd);

        // 현재 날짜를 기반으로 date_prod에 누적 데이터를 저장
        LocalDate currentDate = LocalDate.now();
        Optional<DateProd> optionalDateProd = dateProductRepository.findByLineIdAndDate(lineId, currentDate);

        DateProd dateProd;
        if (optionalDateProd.isPresent()) {
            // 기존 레코드가 있으면 누적 업데이트
            dateProd = optionalDateProd.get();
            dateProd.setSumNormal(dateProd.getSumNormal() + productData.getNormal());
            dateProd.setSumDefective(dateProd.getSumDefective() + productData.getDefective());
            dateProd.setSumReusable(dateProd.getSumReusable() + productData.getReusable());
        } else {
            // 없으면 새로운 레코드를 생성
            dateProd = new DateProd();
            dateProd.setLineId(lineId);
            dateProd.setDate(currentDate);
            dateProd.setSumNormal(productData.getNormal());
            dateProd.setSumDefective(productData.getDefective());
            dateProd.setSumReusable(productData.getReusable());
        }

        // 변경 사항 저장
        dateProductRepository.save(dateProd);
    }

    public void streamRealtimeData(int lineId, SseEmitter emitter) {
        try {
            // 데이터베이스에서 가장 최신의 실시간 데이터를 가져옴
            RealtimeProd latestData = realtimeProductRepository.findTop1ByLineIdOrderByCreatedAtDesc(lineId)
                    .orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));

            // 최신 데이터를 SSE로 전송
            emitter.send(SseEmitter.event()
                    .data(Map.of(
                            "normal", latestData.getNormal(),
                            "defective", latestData.getDefective(),
                            "reusable", latestData.getReusable(),
                            "createdAt", latestData.getCreatedAt().toString()
                    ))
                    .name("realtimeProd")
                    .id(String.valueOf(latestData.getRealtimeProdId()))
                    .reconnectTime(3000)  // 재연결 시간
            );
            emitter.complete(); // 스트리밍 완료
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }
}