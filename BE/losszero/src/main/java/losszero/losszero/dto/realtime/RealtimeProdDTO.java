package losszero.losszero.dto.realtime;

public class RealtimeProdDTO {

    private int normal;
    private int defective;
    private int reusable;

    // getters and setters

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
}