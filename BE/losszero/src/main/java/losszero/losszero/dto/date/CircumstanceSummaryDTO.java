package losszero.losszero.dto.date;

public class CircumstanceSummaryDTO {

    private float maxTemp;
    private float minTemp;
    private float maxHumid;
    private float minHumid;

    // Getters and Setters
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
}
