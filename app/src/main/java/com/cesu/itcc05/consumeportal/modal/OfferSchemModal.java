package com.cesu.itcc05.consumeportal.modal;

public class OfferSchemModal {

    String id="";
    String imageName="";
    String imagePath="";
    String bannerType="";
    String imageClickUrl="";
    String startDate="";
    String enddate="";

    public OfferSchemModal(String id, String imageName, String imagePath, String bannerType, String imageClickUrl, String startDate, String enddate) {
        this.id = id;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.bannerType = bannerType;
        this.imageClickUrl = imageClickUrl;
        this.startDate = startDate;
        this.enddate = enddate;
    }
    public OfferSchemModal(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBannerType() {
        return bannerType;
    }

    public void setBannerType(String bannerType) {
        this.bannerType = bannerType;
    }

    public String getImageClickUrl() {
        return imageClickUrl;
    }

    public void setImageClickUrl(String imageClickUrl) {
        this.imageClickUrl = imageClickUrl;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
