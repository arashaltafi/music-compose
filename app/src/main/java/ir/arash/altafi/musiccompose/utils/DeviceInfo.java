package ir.arash.altafi.musiccompose.utils;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.regex.Pattern;


public final class DeviceInfo {


    private static String deviceHeader = "";

    public static String getJsonDeviceUniqueID(Context ctx) {
        //"    \"Model\": {" + "\n" +
        //"    }" + "\n" +

        return "{" + "\n" +
                //"    \"Model\": {" + "\n" +
                "        \"mac\": \"" + getDeviceWLANMac(ctx) + "\"," + "\n" +
                "        \"model\": \"" + getDeviceModel() + "\"," + "\n" +
                "        \"size\": \"" + getScreenDimension(ctx) + "\"," + "\n" +
                "        \"manufacture\": \"" + getDeviceManufacturer() + "\"," + "\n" +
                "        \"operator\": \"" + getOperatorName(ctx) + "\"," + "\n" +
                "        \"version\": \"" + getDeviceAndroidVersion() + "\"," + "\n" +
                "        \"imei\": \"" + getDeviceIMEI(ctx) + "\"," + "\n" +
                "        \"androidId\": \"" + getAndroidID(ctx) + "\"," + "\n" +
                "        \"mobileUserId\": \"" + getMobileUserId(ctx) + "\"" + "\n" +
                //"    }" + "\n" +
                "}";

    }

    public static HashMap<String, String> getDeviceInfo(Context ctx) {
        HashMap<String, String> deviceMap = new HashMap<String, String>();
        deviceMap.put("Imei", getDeviceIMEI(ctx));
        deviceMap.put("Simno", getDeviceSimNo(ctx));
        deviceMap.put("WifimacAddress", getDeviceWLANMac(ctx));
        deviceMap.put("AndroidId", getAndroidID(ctx));
        deviceMap.put("Imsi", getDeviceSubscriberId(ctx));
        deviceMap.put("CarrierName", getOperatorName(ctx));
        deviceMap.put("Brand", getDeviceManufacturer());
        deviceMap.put("Model", getDeviceModel());
        deviceMap.put("AndroidVersionName", getDeviceAndroidVersion());
        deviceMap.put("AndroidSdk", String.valueOf(android.os.Build.VERSION.SDK_INT));
        deviceMap.put("AndroidVersion", getDeviceAndroidVersion());
        deviceMap.put("Gmail", getGmail(ctx));
        deviceMap.put("deviceId", "7");
        deviceMap.put("UserId", "0");
        return deviceMap;
    }

    public static String getMobileUserId(Context ctx) {
        String id = getAndroidID(ctx);
        int l = id.length();
        if (l > 15) {
            id = id.substring(l - 15);
        }
        return id;
    }

    public static String getGmail(Context ctx) {
        String possibleGmail = "";
        try {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
            Account[] accounts = AccountManager.get(ctx).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    if (account.type.contains("google")) {
                        possibleGmail = account.name;
                        break;
                    }
                }
            }

        } catch (Exception ignored) {
        }

        return possibleGmail;
    }

    public static String getDeviceWLANMac(Context ctx) {
        WifiManager wm = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        @SuppressLint("HardwareIds") String ans = wm.getConnectionInfo().getMacAddress();
        if (ans == null)
            ans = "FF:FF:FF:FF:FF:FF";
        return ans;
    }

    public static String getScreenDimension(Context ctx) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels + "*" + metrics.widthPixels;
    }

    public static String getOperatorName(Context ctx) {
        TelephonyManager manager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String ans = manager.getNetworkOperatorName();
        if (ans == null)
            ans = "IR-TCI";
        return ans;
    }

    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    public static String getDeviceAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    public static String getDeviceIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds") String ans = tm.getDeviceId();
        if (ans == null)
            ans = "Unknown";
        return ans;
    }

    public static String getDeviceSimNo(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds") String ans = tm.getSimSerialNumber();
        if (ans == null)
            ans = "Unknown";
        return ans;
    }

    public static String getDeviceSubscriberId(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds") String ans = tm.getSubscriberId();
        if (ans == null)
            ans = "Unknown";
        return ans;
    }

    @SuppressLint("HardwareIds")
    public static String getAndroidID(Context ctx) {
        return android.provider.Settings.Secure.getString(ctx.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceHeader(Context context, String versionName) {
        if (deviceHeader.equals("")) {
            String userAgent = System.getProperty("http.agent");
            deviceHeader = userAgent + " " + "serial/" + getAndroidID(context) + " ver/" + versionName.replace(" debug", "")
                    .replace(" test", "").replace(" demo", "") + " app/3201";
        }
        return deviceHeader;
    }

    public String getIMEI(Context context) {

        TelephonyManager mngr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("HardwareIds") String imei = mngr.getDeviceId();
        return imei;

    }
}
