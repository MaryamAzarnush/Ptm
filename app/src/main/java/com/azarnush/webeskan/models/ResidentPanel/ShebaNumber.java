package com.azarnush.webeskan.models.ResidentPanel;

public class ShebaNumber {
    int shebaNumberId;
    String shebaNumber;

    public ShebaNumber(int shebaNumberId, String shebaNumber) {
        this.shebaNumberId = shebaNumberId;
        this.shebaNumber = shebaNumber;
    }

    public int getShebaNumberId() {
        return shebaNumberId;
    }

    public void setShebaNumberId(int shebaNumberId) {
        this.shebaNumberId = shebaNumberId;
    }

    public String getShebaNumber() {
        return shebaNumber;
    }

    public void setShebaNumber(String shebaNumber) {
        this.shebaNumber = shebaNumber;
    }
}
