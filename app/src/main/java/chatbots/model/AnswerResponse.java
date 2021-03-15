package chatbots.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnswerResponse {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("ANSWER")
    @Expose
    private String aNSWER;
    @SerializedName("FLAG")
    @Expose
    private String fLAG;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getANSWER() {
        return aNSWER;
    }

    public void setANSWER(String aNSWER) {
        this.aNSWER = aNSWER;
    }

    public String getFLAG() {
        return fLAG;
    }

    public void setFLAG(String fLAG) {
        this.fLAG = fLAG;
    }


}
