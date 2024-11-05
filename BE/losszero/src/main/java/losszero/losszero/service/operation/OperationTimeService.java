package losszero.losszero.service.operation;

import lombok.RequiredArgsConstructor;
import losszero.losszero.dto.operation.OperationTimeDTO;
import losszero.losszero.entity.operation.OperationTime;
import losszero.losszero.repository.operation.OperationTimeRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OperationTimeService {

    private final OperationTimeRepository operationTimeRepository;

    public void startOperation(Long lineId) {
        LocalDate today = LocalDate.now();

        OperationTimeDTO operationTimeDTO = operationTimeRepository.findByLineIdAndOperationDate(lineId, today)
            .map(this::resumeOperation)
            .orElseGet(() -> startNewOperation(lineId, today));

        System.out.println(operationTimeDTO);
    }

    private OperationTimeDTO startNewOperation(Long lineId, LocalDate operationDate) {
        OperationTime operationTime = OperationTime.builder()
                .lineId(lineId)
                .operationDate(operationDate)
                .startTime(LocalDateTime.now())
                .build();

        operationTime = operationTimeRepository.save(operationTime);

        return OperationTimeDTO.builder()
                .lineId(lineId)
                .id(operationTime.getId())
                .operationDate(operationDate)
                .startTime(operationTime.getStartTime())
                .operationTime(Duration.ZERO)
                .build();
    }

    private OperationTimeDTO resumeOperation(OperationTime operationTime) {
        operationTime.setStartTime(LocalDateTime.now());
        operationTime = operationTimeRepository.save(operationTime);

        return OperationTimeDTO.builder()
                .lineId(operationTime.getLineId())
                .id(operationTime.getId())
                .operationDate(operationTime.getOperationDate())
                .startTime(operationTime.getStartTime())
                .operationTime(operationTime.getAccumulatedTime())
                .build();
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

