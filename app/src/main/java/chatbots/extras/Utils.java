package chatbots.extras;

import android.content.Context;
import android.content.SharedPreferences;

public class Utils {

    public static String getChatData(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("botty", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("chatdata", "");
        return a_key;
    }

    public static void setChatData(Context mContext, String isVerified) {
        SharedPreferences preferences = mContext.getSharedPreferences("botty", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("chatdata", isVerified);
        editor.commit();
    }


    public static String getQuestionData(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("botty", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("Question", "");
        return a_key;
    }

    public static void setQuestionData(Context mContext, String isVerified) {
        SharedPreferences preferences = mContext.getSharedPreferences("botty", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Question", isVerified);
        editor.commit();
    }

    public static String getLastCounter(Context mContext) {
        SharedPreferences loginPreferences = mContext.getSharedPreferences("botty", 0); // 0 - for private mode
        String a_key = loginPreferences.getString("lastcounter", "");
        return a_key;
    }

    public static void setLastCounter(Context mContext, String isVerified) {
        SharedPreferences preferences = mContext.getSharedPreferences("botty", 0); // 0 - for private mode
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lastcounter", isVerified);
        editor.commit();
    }
}
