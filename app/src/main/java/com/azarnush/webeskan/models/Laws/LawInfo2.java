package com.azarnush.webeskan.models.Laws;

import io.realm.RealmObject;

public class LawInfo2 extends RealmObject {
    private String lawId;
    private String lawTitle;
    private String lawSummary;
    private String lawContent;
    private String lawSourceLink;
    private String lawTag;
    private String shortKey;
    private String visibleStatusId;
    private String registerDate;
    private String lawGroupRefId;

    public LawInfo2() {

    }

    public LawInfo2(String lawId, String lawTitle, String lawSummary, String lawContent,
                    String lawSourceLink, String lawTag, String shortKey, String visibleStatusId, String registerDate, String lawGroupRefId) {
        this.lawId = lawId;
        this.lawTitle = lawTitle;
        this.lawSummary = lawSummary;
        this.lawContent = lawContent;
        this.lawSourceLink = lawSourceLink;
        this.lawTag = lawTag;
        this.shortKey = shortKey;
        this.visibleStatusId = visibleStatusId;
        this.registerDate = registerDate;
        this.lawGroupRefId = lawGroupRefId;
    }

    public String getLawId() {
        return lawId;
    }

    public void setLawId(String lawId) {
        this.lawId = lawId;
    }

    public String getLawTitle() {
        return lawTitle;
    }

    public void setLawTitle(String lawTitle) {
        this.lawTitle = lawTitle;
    }

    public String getLawSummary() {
        return lawSummary;
    }

    public void setLawSummary(String lawSummary) {
        this.lawSummary = lawSummary;
    }

    public String getLawContent() {
        return lawContent;
    }

    public void setLawContent(String lawContent) {
        this.lawContent = lawContent;
    }

    public String getLawSourceLink() {
        return lawSourceLink;
    }

    public void setLawSourceLink(String lawSourceLink) {
        this.lawSourceLink = lawSourceLink;
    }

    public String getLawTag() {
        return lawTag;
    }

    public void setLawTag(String lawTag) {
        this.lawTag = lawTag;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getVisibleStatusId() {
        return visibleStatusId;
    }

    public void setVisibleStatusId(String visibleStatusId) {
        this.visibleStatusId = visibleStatusId;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getLawGroupRefId() {
        return lawGroupRefId;
    }

    public void setLawGroupRefId(String lawGroupRefId) {
        this.lawGroupRefId = lawGroupRefId;
    }
}
