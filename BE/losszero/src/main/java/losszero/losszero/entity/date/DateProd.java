package losszero.losszero.entity.date;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "date_prod")
public class DateProd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dateProdId;

    @Column(name = "line_id")
    private int lineId;

    @Column(name = "sum_normal")
    private long sumNormal;

    @Column(name = "sum_defective")
    private long sumDefective;

    @Column(name = "sum_reusable")
    private long sumReusable;

    @Column(name = "date", nullable = false)
    private LocalDate date;
}
