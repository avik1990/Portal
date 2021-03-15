package com.cesu.itcc05.consumeportal.modal;

public class BannerModal {

    String id = "";
    String imageName = "";
    String imagePath = "";
    String bannerType = "";
    String imageClickUrl = "";
    String startDate = "";
    String enddate = "";

    String imgURL, clickType, offerType;

    public BannerModal(String id, String imageName, String imagePath, String bannerType, String imageClickUrl, String startDate, String enddate) {
        this.id = id;
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.bannerType = bannerType;
        this.imageClickUrl = imageClickUrl;
        this.startDate = startDate;
        this.enddate = enddate;
    }

    public BannerModal(String imgURL, String clickType,String offerType1) {
        this.imgURL = imgURL;
        this.clickType = clickType;
        this.offerType=offerType1;
    }

    public BannerModal(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getClickType() {
        return clickType;
    }

    public void setClickType(String clickType) {
        this.clickType = clickType;
    }

    public BannerModal() {

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
