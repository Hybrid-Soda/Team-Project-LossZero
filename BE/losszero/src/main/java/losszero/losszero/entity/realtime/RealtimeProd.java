package losszero.losszero.entity.realtime;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "realtime_prod")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealtimeProd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int realtimeProdId;

    @Column(nullable = false)
    private Integer lineId;

    @Column(nullable = false)
    private int normal;

    @Column(nullable = false)
    private int defective;

    @Column(nullable = false)
    private int reusable;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}