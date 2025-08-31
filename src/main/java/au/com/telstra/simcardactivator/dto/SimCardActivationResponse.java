package au.com.telstra.simcardactivator.dto;

public class SimCardActivationResponse {
    private String iccid;
    private String customerEmail;
    private boolean active;

    // Default constructor
    public SimCardActivationResponse() {}

    // Constructor with parameters
    public SimCardActivationResponse(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    // Getters and setters
    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "SimCardActivationResponse{" +
                "iccid='" + iccid + '\'' +
                ", customerEmail='" + customerEmail + '\'' +
                ", active=" + active +
                '}';
    }
} 