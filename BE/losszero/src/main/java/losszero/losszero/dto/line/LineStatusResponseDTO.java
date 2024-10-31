package losszero.losszero.dto.line;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LineStatusResponseDTO {
    private int targetProduct;
    private boolean robotArmsStatus;
    private boolean conveyorStatus;
    private boolean cameraStatus;
}
