package au.com.telstra.simcardactivator.dto;

public class ActuatorResponse {
    private boolean success;

    public ActuatorResponse() {}

    public ActuatorResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "ActuatorResponse{" +
                "successs=" +  success +
                '}';
    }
} 