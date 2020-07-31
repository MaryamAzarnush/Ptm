package com.azarnush.webeskan.models.ResidentPanel;

import java.util.Date;

public class BoardInfo {
    private int boardId;
    private String boardTitle;
    private String boardDescription;
    private String registerDate;
    private String expireDate;
    private String boardPhoto;
    private int acceptStatus;
    private int visibleStatus;
    private String senderUserRefId;
    private int buildingRefId;
    private String boardGroupRefId;

    public BoardInfo(int boardId, String boardTitle, String boardDescription, String registerDate, String expireDate, String boardPhoto, int acceptStatus, int visibleStatus, String senderUserRefId, int buildingRefId, String boardGroupRefId) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardDescription = boardDescription;
        this.registerDate = registerDate;
        this.expireDate = expireDate;
        this.boardPhoto = boardPhoto;
        this.acceptStatus = acceptStatus;
        this.visibleStatus = visibleStatus;
        this.senderUserRefId = senderUserRefId;
        this.buildingRefId = buildingRefId;
        this.boardGroupRefId = boardGroupRefId;
    }

    public BoardInfo(int boardId, String boardTitle, String boardDescription, String registerDate, String expireDate, String boardPhoto, String senderUserRefId, String boardGroupRefId) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardDescription = boardDescription;
        this.registerDate = registerDate;
        this.expireDate = expireDate;
        this.boardPhoto = boardPhoto;
        this.senderUserRefId = senderUserRefId;
        this.boardGroupRefId = boardGroupRefId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getBoardDescription() {
        return boardDescription;
    }

    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getBoardPhoto() {
        return boardPhoto;
    }

    public void setBoardPhoto(String boardPhoto) {
        this.boardPhoto = boardPhoto;
    }

    public int getAcceptStatus() {
        return acceptStatus;
    }

    public void setAcceptStatus(int acceptStatus) {
        this.acceptStatus = acceptStatus;
    }

    public int getVisibleStatus() {
        return visibleStatus;
    }

    public void setVisibleStatus(int visibleStatus) {
        this.visibleStatus = visibleStatus;
    }

    public String getSenderUserRefId() {
        return senderUserRefId;
    }

    public void setSenderUserRefId(String senderUserRefId) {
        this.senderUserRefId = senderUserRefId;
    }

    public int getBuildingRefId() {
        return buildingRefId;
    }

    public void setBuildingRefId(int buildingRefId) {
        this.buildingRefId = buildingRefId;
    }

    public String getBoardGroupRefId() {
        return boardGroupRefId;
    }

    public void setBoardGroupRefId(String boardGroupRefId) {
        this.boardGroupRefId = boardGroupRefId;
    }
}
