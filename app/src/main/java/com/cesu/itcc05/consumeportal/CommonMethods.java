package com.cesu.itcc05.consumeportal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.renderscript.BaseObj;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import android.graphics.Rect;
import android.graphics.Typeface;

import androidx.annotation.RequiresApi;

import com.cesu.itcc05.consumeportal.modal.BannerModal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import static android.os.FileUtils.closeQuietly;

public class CommonMethods {
    public static String getUniqueNumber(String mobileNumber) {
        //return mobileNumber+"1111";
        return "102030405060708";
    }

    public static void loadPaymentPage(Activity activity, String custId, String mobileNum) {
        if (mobileNum == null) {
            mobileNum = "";
        }

        Intent intent = new Intent(activity, PaymentResponsiveActivity.class);
        String testUrl = getPaymentUrl(custId, mobileNum, activity.getApplicationContext());
        intent.putExtra("paymentUrl", testUrl);
        activity.startActivity(intent);
    }

    public static String getAppVersion() {
        String version = BuildConfig.VERSION_NAME;
        return version;
    }

    public static String getPaymentUrl(String custId, String mobileNum, Context context) {
        String url = null;
        try {
            url = getIp(context) + "ConsumerPortal/GetData?intOptionType=2&ConsumerID=" +
                    getEncryptedString(Constants.secretKey, custId) + "&strMobileNo=" +
                    getEncryptedString(Constants.secretKey, mobileNum) + "&isEncrypt=true";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /*http://portal1.tpcentralodisha.com:8080/*/
    public static String getIp(Context context) {
        SharedPreferences sessionlinkurl = context.getSharedPreferences("seslinkval", 0);
        String strurlval = sessionlinkurl.getString("strurladdr", null); // getting String

        return "http://portal.tpcentralodisha.com:8070" + "/";

        /*String normal = "http://portal.tpcentralodisha.com:8070/";
        String disaster = "http://portal1.tpcentralodisha.com:8070/";
        String disaster1 = "http://portal1.tpcentralodisha.com:8070/";
        String disaster2 = "http://portal.tpcentralodisha.com:8070/";
        //Timestamp.class
        Random rand = new Random();
        int randomNum = rand.nextInt(1000);
        if(randomNum%2==0){
            if(randomNum%4==0){
                return disaster1;
            }else{
                return normal;
            }

        }else{
            if(randomNum%3==0){
                return disaster2;
            }else{
                return disaster;
            }
            //return disaster;
        }*/

    }

    public static String encryptedText = "";


    public static String getEncryptedString(String key1, String stringToEncrypt)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        Key key = null;
        try {
            key = generateKey(key1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(stringToEncrypt.getBytes());
        String encryptedValue = android.util.Base64.encodeToString(encVal, 0);
        return encryptedValue;

    }

    public static String getDecryptedString(String key1, String stringToEncrypt)
            throws NoSuchAlgorithmException,
            NoSuchPaddingException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        Key key = null;
        try {
            key = generateKey(key1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = android.util.Base64.decode(stringToEncrypt, 0);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;

    }

    //private static byte[] keyValue = new byte[]{ 'W', 'e', 'l', 'c', 'o', 'm', 'e','t', 'o', 'e', 'n','c', 'r', 'y', 'p', 't' };
    private static Key generateKey(String secret) throws Exception {
        Key key = new SecretKeySpec(secret.getBytes(), "AES");
        return key;
    }

    //The first digit should contain number between 6 to 9.
    //The rest 9 digit can contain any number between 0 to 9.
    public static boolean isValidMobile(String phoneNo) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(phoneNo);
        return (m.find() && m.group().equals(phoneNo));
    }

    public static String getAppLink(Context context) {
        return "https://play.google.com/store/apps/details?id=" + context.getPackageName();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @SuppressLint("NewApi")
    public static String readFileAsBase64String(String path) {
        try {
            InputStream is = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Base64OutputStream b64os = new Base64OutputStream(baos, Base64.DEFAULT);
            byte[] buffer = new byte[8192];
            int bytesRead;
            try {
                while ((bytesRead = is.read(buffer)) > -1) {
                    b64os.write(buffer, 0, bytesRead);
                }
                return baos.toString();
            } catch (IOException e) {
                Log.e("TAG", "Cannot read file " + path, e);
                // Or throw if you prefer
                return "";
            } finally {
                closeQuietly(is);
                closeQuietly(b64os); // This also closes baos
            }
        } catch (FileNotFoundException e) {
            Log.e("TAG", "File not found " + path, e);
            // Or throw if you prefer
            return "";
        }
    }

    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void saveSharedPreferencesLogList(Context context, List<BannerModal> callLog) {
        SharedPreferences mPrefs = context.getSharedPreferences("MyPreference", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(callLog);
        prefsEditor.putString("myJson", json);
        prefsEditor.apply();
    }

    public static List<BannerModal> loadSharedPreferencesLogList(Context context) {
        List<BannerModal> callLog = new ArrayList<BannerModal>();
        SharedPreferences mPrefs = context.getSharedPreferences("MyPreference", context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("myJson", "");
        if (json.isEmpty()) {
            callLog = new ArrayList<BannerModal>();
        } else {
            Type type = new TypeToken<List<BannerModal>>() {
            }.getType();
            callLog = gson.fromJson(json, type);
        }
        return callLog;
    }

    public static boolean checkGPS(Context mContext) {
        boolean gpsEnable = false;
        final LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // buildAlertMessageNoGps(mContext);
            gpsEnable = false;
        } else {
            gpsEnable = true;
        }

        return gpsEnable;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static String getRemoteVersionNumber(Context context) {
        int versionCode = 0;
        String version = "";
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    public static void saveSelectedPosition(Context context, String position) {
        SharedPreferences mPrefs = context.getSharedPreferences("MyPreference", context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        //Gson gson = new Gson();
        // String json = gson.toJson(callLog);
        prefsEditor.putString("ca_position", position);
        prefsEditor.apply();
    }

    public static String getSelectedPosition(Context context) {
        String callLog = "";
        SharedPreferences mPrefs = context.getSharedPreferences("MyPreference", context.MODE_PRIVATE);
        // Gson gson = new Gson();
        callLog = mPrefs.getString("ca_position", "");
        return callLog;
    }


}
