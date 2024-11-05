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
    public void saveCircumstanceData(RealtimeCircumstanceDTO circumstanceData) {
        int lineId = circumstanceData.getLineId();
        float temperature = circumstanceData.getCircumstance().getTemperature();
        float humidity = circumstanceData.getCircumstance().getHumidity();

        RealtimeCircumstance circumstance = new RealtimeCircumstance();
        circumstance.setLineId(lineId);
        circumstance.setTemperature(temperature);
        circumstance.setHumidity(humidity);
        circumstance.setCreatedAt(java.time.LocalDateTime.now());
        realtimeCircumstanceRepository.save(circumstance);

        LocalDate today = LocalDate.now();
        Optional<DateCircumstance> optionalDateCircumstance = dateCircumstanceRepository.findByLineIdAndDate(lineId, today);

        DateCircumstance dateCircumstance;
        if (optionalDateCircumstance.isPresent()) {
            dateCircumstance = optionalDateCircumstance.get();
            dateCircumstance.setMaxTemp(Math.max(dateCircumstance.getMaxTemp(), temperature));
            dateCircumstance.setMinTemp(Math.min(dateCircumstance.getMinTemp(), temperature));
            dateCircumstance.setMaxHumid(Math.max(dateCircumstance.getMaxHumid(), humidity));
            dateCircumstance.setMinHumid(Math.min(dateCircumstance.getMinHumid(), humidity));
        } else {
            dateCircumstance = new DateCircumstance();
            dateCircumstance.setLineId(lineId);
            dateCircumstance.setDate(today);
            dateCircumstance.setMaxTemp(temperature);
            dateCircumstance.setMinTemp(temperature);
            dateCircumstance.setMaxHumid(humidity);
            dateCircumstance.setMinHumid(humidity);
        }
        dateCircumstanceRepository.save(dateCircumstance);
    }
}