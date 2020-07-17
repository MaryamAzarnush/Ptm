package com.azarnush.webeskan.models.Laws;

public class LawGroup {


    private String lawGroupTitle;
    private String lawGroupPhoto;
    private String lawGroupId;
    private String visibleStatusId;
    private String sort;

    public LawGroup(String lawGroupTitle, String lawGroupPhoto, String lawGroupId, String visibleStatusId, String sort) {
        this.lawGroupTitle = lawGroupTitle;
        this.lawGroupPhoto = lawGroupPhoto;
        this.lawGroupId = lawGroupId;
        this.visibleStatusId = visibleStatusId;
        this.sort = sort;
    }

    public String getLawGroupTitle() {
        return lawGroupTitle;
    }

    public void setLawGroupTitle(String lawGroupTitle) {
        this.lawGroupTitle = lawGroupTitle;
    }

    public String getLawGroupPhoto() {
        return lawGroupPhoto;
    }

    public void setLawGroupPhoto(String lawGroupPhoto) {
        this.lawGroupPhoto = lawGroupPhoto;
    }

    public String getLawGroupId() {
        return lawGroupId;
    }

    public void setLawGroupId(String lawGroupId) {
        this.lawGroupId = lawGroupId;
    }

    public String getVisibleStatusId() {
        return visibleStatusId;
    }

    public void setVisibleStatusId(String visibleStatusId) {
        this.visibleStatusId = visibleStatusId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}