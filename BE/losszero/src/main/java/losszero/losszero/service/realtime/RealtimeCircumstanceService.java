package losszero.losszero.service.realtime;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.realtime.RealtimeCircumstanceDTO;
import losszero.losszero.entity.date.DateCircumstance;
import losszero.losszero.entity.realtime.RealtimeCircumstance;
import losszero.losszero.repository.date.DateCircumstanceRepository;
import losszero.losszero.repository.realtime.RealtimeCircumstanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RealtimeCircumstanceService {

    private final RealtimeCircumstanceRepository realtimeCircumstanceRepository;
    private final DateCircumstanceRepository dateCircumstanceRepository;

    @Transactional
    public void saveCircumstanceData(RealtimeCircumstanceDTO circumstanceData) {
        saveRealtimeCircumstance(circumstanceData);
        updateDateCircumstance(circumstanceData);
    }

    private void saveRealtimeCircumstance(RealtimeCircumstanceDTO data) {
        RealtimeCircumstance circumstance = RealtimeCircumstance.builder()
                .lineId(data.getLineId())
                .temperature(data.getCircumstance().getTemperature())
                .humidity(data.getCircumstance().getHumidity())
                .createdAt(LocalDateTime.now())
                .build();
        realtimeCircumstanceRepository.save(circumstance);
    }

    private void updateDateCircumstance(RealtimeCircumstanceDTO data) {
        int lineId = data.getLineId();
        float temperature = data.getCircumstance().getTemperature();
        float humidity = data.getCircumstance().getHumidity();
        LocalDate today = LocalDate.now();

        DateCircumstance dateCircumstance = dateCircumstanceRepository.findByLineIdAndDate(lineId, today)
                .map(existing -> updateExistingDateCircumstance(existing, temperature, humidity))
                .orElseGet(() -> createNewDateCircumstance(lineId, today, temperature, humidity));

        dateCircumstanceRepository.save(dateCircumstance);
    }

    private DateCircumstance updateExistingDateCircumstance(DateCircumstance existing, float temperature, float humidity) {
        existing.setMaxTemp(Math.max(existing.getMaxTemp(), temperature));
        existing.setMinTemp(Math.min(existing.getMinTemp(), temperature));
        existing.setMaxHumid(Math.max(existing.getMaxHumid(), humidity));
        existing.setMinHumid(Math.min(existing.getMinHumid(), humidity));
        return existing;
    }

    private DateCircumstance createNewDateCircumstance(int lineId, LocalDate date, float temperature, float humidity) {
        return DateCircumstance.builder()
                .lineId(lineId)
                .date(date)
                .maxTemp(temperature)
                .minTemp(temperature)
                .maxHumid(humidity)
                .minHumid(humidity)
                .build();
    }
}