package com.azarnush.webeskan.models.ResidentPanel;

public class Unit {
    Boolean isAccepted;
    String auto_number;
    String buildingTitle;
    String unitTitle;
    Integer residenceRefId;

    public Unit(Boolean isAccepted, String auto_number, String buildingTitle, String unitTitle, Integer residenceRefId) {
        this.isAccepted = isAccepted;
        this.auto_number = auto_number;
        this.buildingTitle = buildingTitle;
        this.unitTitle = unitTitle;
        this.residenceRefId = residenceRefId;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public String getAuto_number() {
        return auto_number;
    }

    public void setAuto_number(String auto_number) {
        this.auto_number = auto_number;
    }

    public String getBuildingTitle() {
        return buildingTitle;
    }

    public void setBuildingTitle(String buildingTitle) {
        this.buildingTitle = buildingTitle;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public Integer getResidenceRefId() {
        return residenceRefId;
    }

    public void setResidenceRefId(Integer residenceRefId) {
        this.residenceRefId = residenceRefId;
    }
}
