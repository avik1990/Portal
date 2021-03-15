package chatbots.model;

import com.cesu.itcc05.consumeportal.modal.BannerModal;

import java.util.List;

public class ChatResponseModel {

    String qID;
    String questions;

    String answerID;
    String answers;

    int Itemtype;

    String UserTypedquestions;

    String Inputtype = "";

    String latitude;
    String longitude;

    List<QuestionResponse> listQuestions;
    List<AnswerResponse> listAnswers;
    List<BannerModal> listImages;
    List<String> listPhone;
    int likeDislike = -1;
    String date;


    public ChatResponseModel(String answerID, String answers, int itemtype,int likeDislike1,String date1) {
        this.answerID = answerID;
        this.answers = answers;
        Itemtype = itemtype;
        this.likeDislike=likeDislike1;
        this.date = date1;
    }

    public ChatResponseModel(String inputtype, int itemtype, String userTypedquestions,int likeDislike1,String date1) {
        Itemtype = itemtype;
        UserTypedquestions = userTypedquestions;
        Inputtype = inputtype;
        this.likeDislike=likeDislike1;
        this.date=date1;
    }

    public ChatResponseModel(List<BannerModal> listImages, int itemtype,int likeDislike1,String date1) {
        Itemtype = itemtype;
        this.listImages = listImages;
        this.likeDislike=likeDislike1;
        this.date=date1;
    }

    public ChatResponseModel(int itemtype, String inputtype, String latitude, String longitude, String userTypedquestions,int likeDislike1,String date1) {
        Itemtype = itemtype;
        Inputtype = inputtype;
        this.latitude = latitude;
        this.longitude = longitude;
        UserTypedquestions = userTypedquestions;
        this.likeDislike=likeDislike1;
        this.date=date1;
    }


    public ChatResponseModel(int itemtype, List<QuestionResponse> listQuestions,int likeDislike1,String date1) {
        Itemtype = itemtype;
        this.listQuestions = listQuestions;
        this.likeDislike=likeDislike1;
        this.date=date1;
    }


    public ChatResponseModel(int itemTypeLocations, String s, List<String> answerResponse,int likeDislike1,String date1) {
        Itemtype = itemTypeLocations;
        this.listPhone = answerResponse;
        this.likeDislike=likeDislike1;
        this.date=date1;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLikeDislike() {
        return likeDislike;
    }

    public void setLikeDislike(int likeDislike) {
        this.likeDislike = likeDislike;
    }

    public List<BannerModal> getListImages() {
        return listImages;
    }


    public List<String> getListPhone() {
        return listPhone;
    }

    public void setListPhone(List<String> listPhone) {
        this.listPhone = listPhone;
    }

    public void setListImages(List<BannerModal> listImages) {
        this.listImages = listImages;
    }

    public List<AnswerResponse> getListAnswers() {
        return listAnswers;
    }

    public void setListAnswers(List<AnswerResponse> listAnswers) {
        this.listAnswers = listAnswers;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getInputtype() {
        return Inputtype;
    }

    public void setInputtype(String inputtype) {
        Inputtype = inputtype;
    }

    public String getqID() {
        return qID;
    }

    public void setqID(String qID) {
        this.qID = qID;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswerID() {
        return answerID;
    }

    public void setAnswerID(String answerID) {
        this.answerID = answerID;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public int getItemtype() {
        return Itemtype;
    }

    public void setItemtype(int itemtype) {
        Itemtype = itemtype;
    }

    public List<QuestionResponse> getListQuestions() {
        return listQuestions;
    }

    public void setListQuestions(List<QuestionResponse> listQuestions) {
        this.listQuestions = listQuestions;
    }

    public String getUserTypedquestions() {
        return UserTypedquestions;
    }

    public void setUserTypedquestions(String userTypedquestions) {
        UserTypedquestions = userTypedquestions;
    }
}
