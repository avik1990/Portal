package com.cesu.itcc05.consumeportal.modal;

public class ModalComplainDetails {

    String Complaint_date_time="";
    String ComplaintID="";
    String ComplaintType="";
    String ComplaintSubType="";
    String ComplaintDetails="";
    String  ActionTaken="";
    String ResolvedDateTime="";
    String ComplaintStatus="";

    public ModalComplainDetails(String complaint_date_time, String complaintID, String complaintType, String complaintSubType, String complaintDetails, String actionTaken, String resolvedDateTime, String complaintStatus) {
        Complaint_date_time = complaint_date_time;
        ComplaintID = complaintID;
        ComplaintType = complaintType;
        ComplaintSubType = complaintSubType;
        ComplaintDetails = complaintDetails;
        ActionTaken = actionTaken;
        ResolvedDateTime = resolvedDateTime;
        ComplaintStatus = complaintStatus;
    }

    public ModalComplainDetails(){

    }

    public String getComplaint_date_time() {
        return Complaint_date_time;
    }

    public void setComplaint_date_time(String complaint_date_time) {
        Complaint_date_time = complaint_date_time;
    }

    public String getComplaintID() {
        return ComplaintID;
    }

    public void setComplaintID(String complaintID) {
        ComplaintID = complaintID;
    }

    public String getComplaintType() {
        return ComplaintType;
    }

    public void setComplaintType(String complaintType) {
        ComplaintType = complaintType;
    }

    public String getComplaintSubType() {
        return ComplaintSubType;
    }

    public void setComplaintSubType(String complaintSubType) {
        ComplaintSubType = complaintSubType;
    }

    public String getComplaintDetails() {
        return ComplaintDetails;
    }

    public void setComplaintDetails(String complaintDetails) {
        ComplaintDetails = complaintDetails;
    }

    public String getActionTaken() {
        return ActionTaken;
    }

    public void setActionTaken(String actionTaken) {
        ActionTaken = actionTaken;
    }

    public String getResolvedDateTime() {
        return ResolvedDateTime;
    }

    public void setResolvedDateTime(String resolvedDateTime) {
        ResolvedDateTime = resolvedDateTime;
    }

    public String getComplaintStatus() {
        return ComplaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        ComplaintStatus = complaintStatus;
    }
}
