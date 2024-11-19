package losszero.losszero.service.line;

import losszero.losszero.dto.line.LineStatusResponseDTO;
import losszero.losszero.dto.line.SetTargetProductDTO;
import losszero.losszero.entity.line.Line;
import losszero.losszero.repository.line.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LineService {

    @Autowired
    private LineRepository lineRepository;

    public LineStatusResponseDTO getLineStatus(int lineId) {
        Optional<Line> line = lineRepository.findById(lineId);

        if (!line.isPresent()) {
            throw new IllegalArgumentException("라인을 찾을 수 없습니다.");
        }

        LineStatusResponseDTO responseDTO = new LineStatusResponseDTO();
        responseDTO.setTargetProduct(line.get().getTargetProduct());
        responseDTO.setRobotArmsStatus(line.get().isRobotArmsStatus());
        responseDTO.setConveyorStatus(line.get().isConveyorStatus());
        responseDTO.setCameraStatus(line.get().isCameraStatus());

        return responseDTO;
    }
    public void setTargetProduct(int lineId, SetTargetProductDTO dto) {
        Optional<Line> optionalLine = lineRepository.findById(lineId);

        if (!optionalLine.isPresent()) {
            throw new IllegalArgumentException("해당 라인을 찾을 수 없습니다.");
        }

        Line line = optionalLine.get();
        line.setTargetProduct(dto.getTargetProduct());
        lineRepository.save(line); // 목표 생산량 업데이트
    }
}