package chatbots.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionResponse {
    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("QUESTION")
    @Expose
    private String qUESTION;
    @SerializedName("FLAG")
    @Expose
    private String fLAG;

    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getqUESTION() {
        return qUESTION;
    }

    public void setqUESTION(String qUESTION) {
        this.qUESTION = qUESTION;
    }

    public String getfLAG() {
        return fLAG;
    }

    public void setfLAG(String fLAG) {
        this.fLAG = fLAG;
    }
}
