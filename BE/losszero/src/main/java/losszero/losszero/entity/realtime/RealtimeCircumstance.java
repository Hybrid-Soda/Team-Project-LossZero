package losszero.losszero.entity.realtime;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "realtime_circumstance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealtimeCircumstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int realtimeCircumstanceId;

    @Column(nullable = false)
    private Integer lineId;

    @Column(nullable = false)
    private float temperature;

    @Column(nullable = false)
    private float humidity;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
