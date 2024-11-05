package losszero.losszero.entity.date;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "date_circumstance")
public class DateCircumstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dateCircumstanceId;

    @Column(name = "line_id", nullable = false)
    private int lineId;

    @Column(name = "max_temp", nullable = false)
    private float maxTemp;

    @Column(name = "min_temp", nullable = false)
    private float minTemp;

    @Column(name = "max_humid", nullable = false)
    private float maxHumid;

    @Column(name = "min_humid", nullable = false)
    private float minHumid;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}