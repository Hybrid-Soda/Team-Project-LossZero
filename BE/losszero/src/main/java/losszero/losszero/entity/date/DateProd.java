package losszero.losszero.entity.date;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
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

    // Getters and Setters
    public int getDateProdId() {
        return dateProdId;
    }

    public void setDateProdId(int dateProdId) {
        this.dateProdId = dateProdId;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public long getSumNormal() {
        return sumNormal;
    }

    public void setSumNormal(long sumNormal) {
        this.sumNormal = sumNormal;
    }

    public long getSumDefective() {
        return sumDefective;
    }

    public void setSumDefective(long sumDefective) {
        this.sumDefective = sumDefective;
    }

    public long getSumReusable() {
        return sumReusable;
    }

    public void setSumReusable(long sumReusable) {
        this.sumReusable = sumReusable;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
