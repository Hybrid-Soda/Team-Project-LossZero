package losszero.losszero.service.operation;

import losszero.losszero.dto.operation.OperationTimeDTO;
import losszero.losszero.entity.operation.OperationTime;
import losszero.losszero.repository.operation.OperationTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OperationTimeService {

    @Autowired
    private OperationTimeRepository operationTimeRepository;

    public OperationTimeDTO startOperation(Long lineId) {
        LocalDate today = LocalDate.now();

        Optional<OperationTime> existingOperation = operationTimeRepository.findByLineIdAndOperationDate(lineId, today);

        if (existingOperation.isEmpty()) {
            // 새로 가동 시작
            OperationTime operationTime = new OperationTime();
            operationTime.setLineId(lineId);
            operationTime.setOperationDate(today);
            operationTime.setStartTime(LocalDateTime.now());
            operationTime.setAccumulatedTime(Duration.ZERO); // 새로 시작할 때는 0
            operationTime = operationTimeRepository.save(operationTime);

            return new OperationTimeDTO(lineId, operationTime.getId(), today, operationTime.getStartTime(), Duration.ZERO);
        } else {
            // 재가동
            OperationTime operationTime = existingOperation.get();
            operationTime.setStartTime(LocalDateTime.now()); // 재가동 시작 시각 변경
            operationTime = operationTimeRepository.save(operationTime);

            return new OperationTimeDTO(lineId, operationTime.getId(), today, operationTime.getStartTime(), operationTime.getAccumulatedTime());
        }
    }

    public OperationTimeDTO endOperation(Long lineId, Long cycleProdId) {
        OperationTime operationTime = operationTimeRepository.findById(cycleProdId)
                .orElseThrow(() -> new IllegalArgumentException("Cycle not found"));

        // 가동 종료 시, 현재까지 가동된 시간을 추가
        Duration currentDuration = Duration.between(operationTime.getStartTime(), LocalDateTime.now());
        operationTime.setAccumulatedTime(operationTime.getAccumulatedTime().plus(currentDuration));
        operationTimeRepository.save(operationTime);

        return new OperationTimeDTO(lineId, cycleProdId, operationTime.getOperationDate(), operationTime.getStartTime(), operationTime.getAccumulatedTime());
    }

    public Duration getOperationTime(Long lineId, LocalDate date) {
        OperationTime operationTime = operationTimeRepository.findByLineIdAndOperationDate(lineId, date)
                .orElseThrow(() -> new IllegalArgumentException("Operation not found"));

        Duration accumulatedTime = operationTime.getAccumulatedTime();
        // 실시간 가동시간 계산
        accumulatedTime = accumulatedTime.plus(Duration.between(operationTime.getStartTime(), LocalDateTime.now()));

        return accumulatedTime;
    }
}

