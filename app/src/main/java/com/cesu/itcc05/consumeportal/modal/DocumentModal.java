package com.cesu.itcc05.consumeportal.modal;

public class DocumentModal {
    String ID="";
    String DOC_NAME="";
    String DOC_DESCR="";
    String DOC_PATH="";

    public DocumentModal(String ID, String DOC_NAME, String DOC_DESCR, String DOC_PATH) {
        this.ID = ID;
        this.DOC_NAME = DOC_NAME;
        this.DOC_DESCR = DOC_DESCR;
        this.DOC_PATH = DOC_PATH;
    }

    public DocumentModal(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDOC_NAME() {
        return DOC_NAME;
    }

    public void setDOC_NAME(String DOC_NAME) {
        this.DOC_NAME = DOC_NAME;
    }

    public String getDOC_DESCR() {
        return DOC_DESCR;
    }

    public void setDOC_DESCR(String DOC_DESCR) {
        this.DOC_DESCR = DOC_DESCR;
    }

    public String getDOC_PATH() {
        return DOC_PATH;
    }

    public void setDOC_PATH(String DOC_PATH) {
        this.DOC_PATH = DOC_PATH;
    }
}
