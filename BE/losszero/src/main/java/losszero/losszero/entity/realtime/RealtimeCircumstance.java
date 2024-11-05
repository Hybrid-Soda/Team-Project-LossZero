package losszero.losszero.entity.realtime;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "realtime_circumstance")
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

    // Getters and Setters
    public int getRealtimeCircumstanceId() {
        return realtimeCircumstanceId;
    }

    public void setRealtimeCircumstanceId(int realtimeCircumstanceId) {
        this.realtimeCircumstanceId = realtimeCircumstanceId;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
