package losszero.losszero.dto.realtime;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
public class TodayRealtimeProductDTO {
    private int lineId;
    private List<ProductDTO> products;
}
