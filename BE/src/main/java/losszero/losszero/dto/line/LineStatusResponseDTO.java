package losszero.losszero.dto.line;

public class LineStatusResponseDTO {
    private int targetProduct;
    private boolean robotArmsStatus;
    private boolean conveyorStatus;
    private boolean cameraStatus;

    // Getters and Setters
    public int getTargetProduct() {
        return targetProduct;
    }

    public void setTargetProduct(int targetProduct) {
        this.targetProduct = targetProduct;
    }

    public boolean isRobotArmsStatus() {
        return robotArmsStatus;
    }

    public void setRobotArmsStatus(boolean robotArmsStatus) {
        this.robotArmsStatus = robotArmsStatus;
    }

    public boolean isConveyorStatus() {
        return conveyorStatus;
    }

    public void setConveyorStatus(boolean conveyorStatus) {
        this.conveyorStatus = conveyorStatus;
    }

    public boolean isCameraStatus() {
        return cameraStatus;
    }

    public void setCameraStatus(boolean cameraStatus) {
        this.cameraStatus = cameraStatus;
    }
}
