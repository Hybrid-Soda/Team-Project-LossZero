package losszero.losszero.service.operation;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.operation.OperationTimeDTO;
import losszero.losszero.entity.operation.OperationTime;
import losszero.losszero.repository.operation.OperationTimeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationTimeService {

    private final OperationTimeRepository operationTimeRepository;

    public void startOperation(Long lineId) {
        LocalDate today = LocalDate.now();

        Optional<OperationTime> existingOperation = operationTimeRepository.findByLineIdAndOperationDate(lineId, today);

        if (existingOperation.isEmpty()) {
            startNewOperation(lineId, today);
        } else {
            resumeOperation(existingOperation.get());
        }
    }

    private void startNewOperation(Long lineId, LocalDate operationDate) {
        OperationTime operationTime = OperationTime.builder()
                .lineId(lineId)
                .operationDate(operationDate)
                .startTime(LocalDateTime.now())
                .build();
        operationTime.setAccumulatedTime(Duration.ZERO);
        operationTimeRepository.save(operationTime);
    }

    private void resumeOperation(OperationTime operationTime) {
        operationTime.setStartTime(LocalDateTime.now());
        operationTimeRepository.save(operationTime);
    }

    public void endOperation(Long lineId) {
        OperationTime operationTime = operationTimeRepository.findTopByLineIdOrderByIdDesc(lineId)
                .orElseThrow(() -> new IllegalArgumentException("Cycle not found"));

        Duration currentDuration = Duration.between(operationTime.getStartTime(), LocalDateTime.now());
        operationTime.setAccumulatedTime(operationTime.getAccumulatedTime().plus(currentDuration));
        operationTimeRepository.save(operationTime);
    }

    public Duration getOperationTime(Long lineId, LocalDate date) {
        OperationTime operationTime = operationTimeRepository.findByLineIdAndOperationDate(lineId, date)
                .orElseThrow(() -> new IllegalArgumentException("Operation not found"));

        Duration accumulatedTime = operationTime.getAccumulatedTime();
        accumulatedTime = accumulatedTime.plus(Duration.between(operationTime.getStartTime(), LocalDateTime.now()));

        return accumulatedTime;
    }
}

