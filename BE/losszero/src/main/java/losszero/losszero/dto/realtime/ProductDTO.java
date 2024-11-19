package losszero.losszero.dto.realtime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDTO {
    private int normal;
    private int defective;
    private int reusable;
    private String createdAt;
}