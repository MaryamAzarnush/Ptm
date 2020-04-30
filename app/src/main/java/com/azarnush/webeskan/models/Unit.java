package com.azarnush.webeskan.models;

public class Unit {
    String auto_number;
    String buildingTitle;
    String unitTitle;

    public Unit(String auto_number, String buildingTitle, String unitTitle) {
        this.auto_number = auto_number;
        this.buildingTitle = buildingTitle;
        this.unitTitle = unitTitle;
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
}
