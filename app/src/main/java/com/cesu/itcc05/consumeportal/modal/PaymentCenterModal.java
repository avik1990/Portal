package com.cesu.itcc05.consumeportal.modal;

public class PaymentCenterModal {

    String officeId="";
    String officeAddress="";
    String officeLat="";
    String officeLong="";

    public PaymentCenterModal(String officeId, String officeAddress, String officeLat, String officeLong) {
        this.officeId = officeId;
        this.officeAddress = officeAddress;
        this.officeLat = officeLat;
        this.officeLong = officeLong;
    }

    public PaymentCenterModal(){}

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOfficeLat() {
        return officeLat;
    }

    public void setOfficeLat(String officeLat) {
        this.officeLat = officeLat;
    }

    public String getOfficeLong() {
        return officeLong;
    }

    public void setOfficeLong(String officeLong) {
        this.officeLong = officeLong;
    }
}
