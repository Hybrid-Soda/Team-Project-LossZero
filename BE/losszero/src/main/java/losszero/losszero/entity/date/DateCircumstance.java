package losszero.losszero.entity.date;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
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

    // Getters and Setters
    public int getDateCircumstanceId() {
        return dateCircumstanceId;
    }

    public void setDateCircumstanceId(int dateCircumstanceId) {
        this.dateCircumstanceId = dateCircumstanceId;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxHumid() {
        return maxHumid;
    }

    public void setMaxHumid(float maxHumid) {
        this.maxHumid = maxHumid;
    }

    public float getMinHumid() {
        return minHumid;
    }

    public void setMinHumid(float minHumid) {
        this.minHumid = minHumid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}