package losszero.losszero.service.realtime;

import jakarta.transaction.Transactional;
import losszero.losszero.dto.realtime.RealtimeCircumstanceDTO;
import losszero.losszero.entity.date.DateCircumstance;
import losszero.losszero.entity.realtime.RealtimeCircumstance;
import losszero.losszero.repository.date.DateCircumstanceRepository;
import losszero.losszero.repository.realtime.RealtimeCircumstanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class RealtimeCircumstanceService {

    @Autowired
    private RealtimeCircumstanceRepository realtimeCircumstanceRepository;

    @Autowired
    private DateCircumstanceRepository dateCircumstanceRepository;

    @Transactional
    public void saveCircumstanceData(int lineId, RealtimeCircumstanceDTO data) {
        RealtimeCircumstance circumstance = new RealtimeCircumstance();
        circumstance.setLineId(lineId);
        circumstance.setTemperature(data.getTemperature());
        circumstance.setHumidity(data.getHumidity());
        circumstance.setCreatedAt(java.time.LocalDateTime.now());
        realtimeCircumstanceRepository.save(circumstance);

        LocalDate today = LocalDate.now();
        Optional<DateCircumstance> optionalDateCircumstance = dateCircumstanceRepository.findByLineIdAndDate(lineId, today);

        DateCircumstance dateCircumstance;
        if (optionalDateCircumstance.isPresent()) {
            dateCircumstance = optionalDateCircumstance.get();
            dateCircumstance.setMaxTemp(Math.max(dateCircumstance.getMaxTemp(), data.getTemperature()));
            dateCircumstance.setMinTemp(Math.min(dateCircumstance.getMinTemp(), data.getTemperature()));
            dateCircumstance.setMaxHumid(Math.max(dateCircumstance.getMaxHumid(), data.getHumidity()));
            dateCircumstance.setMinHumid(Math.min(dateCircumstance.getMinHumid(), data.getHumidity()));
        } else {
            dateCircumstance = new DateCircumstance();
            dateCircumstance.setLineId(lineId);
            dateCircumstance.setDate(today);
            dateCircumstance.setMaxTemp(data.getTemperature());
            dateCircumstance.setMinTemp(data.getTemperature());
            dateCircumstance.setMaxHumid(data.getHumidity());
            dateCircumstance.setMinHumid(data.getHumidity());
        }
        dateCircumstanceRepository.save(dateCircumstance);
    }

    public void streamRealtimeData(int lineId, SseEmitter emitter) {
        try {
            // 최신 실시간 데이터 가져오기
            RealtimeCircumstance latestData = realtimeCircumstanceRepository.findTop1ByLineIdOrderByCreatedAtDesc(lineId)
                    .orElseThrow(() -> new IllegalArgumentException("데이터가 없습니다."));

            // 오늘 날짜의 최저/최고 온습도 가져오기
            LocalDate today = LocalDate.now();
            Optional<DateCircumstance> optionalDateCircumstance = dateCircumstanceRepository.findByLineIdAndDate(lineId, today);

            // 기본값 설정
            float maxTemp = -Float.MAX_VALUE;
            float minTemp = Float.MAX_VALUE;
            float maxHumid = -Float.MAX_VALUE;
            float minHumid = Float.MAX_VALUE;

            if (optionalDateCircumstance.isPresent()) {
                DateCircumstance dateCircumstance = optionalDateCircumstance.get();
                maxTemp = dateCircumstance.getMaxTemp();
                minTemp = dateCircumstance.getMinTemp();
                maxHumid = dateCircumstance.getMaxHumid();
                minHumid = dateCircumstance.getMinHumid();
            }

            // SSE 전송
            emitter.send(SseEmitter.event()
                    .data(Map.of(
                            "temperature", latestData.getTemperature(),
                            "humidity", latestData.getHumidity(),
                            "createdAt", latestData.getCreatedAt().toString(),
                            "maxTemp", maxTemp,
                            "minTemp", minTemp,
                            "maxHumid", maxHumid,
                            "minHumid", minHumid
                    ))
                    .name("realtimeCircumstance")
                    .reconnectTime(3000)
            );
            emitter.complete();
        } catch (Exception e) {
            emitter.completeWithError(e);
        }
    }
}