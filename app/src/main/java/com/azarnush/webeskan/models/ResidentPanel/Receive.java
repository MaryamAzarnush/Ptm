package com.azarnush.webeskan.models.ResidentPanel;

public class Receive {
    int receiveId;
    String receiveDate;
    Double receiveAmount;
    String receiveDescription;
    String receiveType;
    String transactionNumber;

//    int receiveTypeId;
//    int buildingRefId;
//    int commonDetailedAccountRefId;
//    String DescriptionExtra;
//    String unitTitle;
//    String personType;
//    String commonDetailedAccount;

    public Receive(int receiveId, String receiveDate, Double receiveAmount, String receiveDescription, String recieveType, String transactionNumber) {
        this.receiveId = receiveId;
        this.receiveDate = receiveDate;
        this.receiveAmount = receiveAmount;
        this.receiveDescription = receiveDescription;
        this.receiveType = recieveType;
        this.transactionNumber = transactionNumber;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public String getReceiveDescription() {
        return receiveDescription;
    }

    public void setReceiveDescription(String receiveDescription) {
        this.receiveDescription = receiveDescription;
    }

    public String getRecieveType() {
        return receiveType;
    }

    public void setRecieveType(String recieveType) {
        this.receiveType = recieveType;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

//    public int getReceiveTypeId() {
//        return receiveTypeId;
//    }
//
//    public void setReceiveTypeId(int receiveTypeId) {
//        this.receiveTypeId = receiveTypeId;
//    }
//
//    public int getBuildingRefId() {
//        return buildingRefId;
//    }
//
//    public void setBuildingRefId(int buildingRefId) {
//        this.buildingRefId = buildingRefId;
//    }
//
//    public int getCommonDetailedAccountRefId() {
//        return commonDetailedAccountRefId;
//    }
//
//    public void setCommonDetailedAccountRefId(int commonDetailedAccountRefId) {
//        this.commonDetailedAccountRefId = commonDetailedAccountRefId;
//    }
//
//    public String getDescriptionExtra() {
//        return DescriptionExtra;
//    }
//
//    public void setDescriptionExtra(String descriptionExtra) {
//        DescriptionExtra = descriptionExtra;
//    }
//
//    public String getUnitTitle() {
//        return unitTitle;
//    }
//
//    public void setUnitTitle(String unitTitle) {
//        this.unitTitle = unitTitle;
//    }
//
//    public String getPersonType() {
//        return personType;
//    }
//
//    public void setPersonType(String personType) {
//        this.personType = personType;
//    }
//
//    public String getCommonDetailedAccount() {
//        return commonDetailedAccount;
//    }
//
//    public void setCommonDetailedAccount(String commonDetailedAccount) {
//        this.commonDetailedAccount = commonDetailedAccount;
//    }
}
