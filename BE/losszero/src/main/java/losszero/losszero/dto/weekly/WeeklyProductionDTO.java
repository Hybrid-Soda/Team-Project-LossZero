package losszero.losszero.dto.weekly;

import java.time.LocalDate;

public class WeeklyProductionDTO {
    private LocalDate date;
    private long sumNormal;
    private long sumDefective;
    private long sumReusable;
    private long total;

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
