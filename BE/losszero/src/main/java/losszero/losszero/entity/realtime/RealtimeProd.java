package losszero.losszero.entity.realtime;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "realtime_prod")
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

    // 기본 생성자
    public RealtimeProd() {}

    // Getter and Setter

    public int getRealtimeProdId() {
        return realtimeProdId;
    }

    public void setRealtimeProdId(int realtimeProdId) {
        this.realtimeProdId = realtimeProdId;
    }

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getDefective() {
        return defective;
    }

    public void setDefective(int defective) {
        this.defective = defective;
    }

    public int getReusable() {
        return reusable;
    }

    public void setReusable(int reusable) {
        this.reusable = reusable;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}