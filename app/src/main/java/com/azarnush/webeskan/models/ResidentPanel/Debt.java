package com.azarnush.webeskan.models.ResidentPanel;

public class Debt {
    public boolean checkDebt;
    public int actionType;
    public int debtId;
    public double payableDebtAmount;
    public double debtAmount;
    public String debtDate;
    public String debtTitle;
    public int residenceId;

    public Debt(boolean checkDebt, int actionType, int debtId, double payableDebtAmount, double debtAmount, String debtDate, String debtTitle, int residenceId) {
        this.checkDebt = checkDebt;
        this.actionType = actionType;
        this.debtId = debtId;
        this.payableDebtAmount = payableDebtAmount;
        this.debtAmount = debtAmount;
        this.debtDate = debtDate;
        this.debtTitle = debtTitle;
        this.residenceId = residenceId;
    }

    public boolean isCheckDebt() {
        return checkDebt;
    }

    public void setCheckDebt(boolean checkDebt) {
        this.checkDebt = checkDebt;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getDebtId() {
        return debtId;
    }

    public void setDebtId(int debtId) {
        this.debtId = debtId;
    }

    public double getPayableDebtAmount() {
        return payableDebtAmount;
    }

    public void setPayableDebtAmount(double payableDebtAmount) {
        this.payableDebtAmount = payableDebtAmount;
    }

    public double getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(double debtAmount) {
        this.debtAmount = debtAmount;
    }

    public String getDebtDate() {
        return debtDate;
    }

    public void setDebtDate(String debtDate) {
        this.debtDate = debtDate;
    }

    public String getDebtTitle() {
        return debtTitle;
    }

    public void setDebtTitle(String debtTitle) {
        this.debtTitle = debtTitle;
    }

    public int getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(int residenceId) {
        this.residenceId = residenceId;
    }
}
