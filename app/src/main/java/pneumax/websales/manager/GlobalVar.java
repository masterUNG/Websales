package pneumax.websales.manager;

import android.util.Log;
import android.widget.EditText;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by sitrach on 21/08/2017.
 */

public class GlobalVar {

    public static String getSuccess = "Success";
    public static String getUnSuccess = "UnSuccess";
    public static String getInsertDuplicate = "InsertDuplicate";
    public static String getCurrentModifyTitle = "CurrentModifyTitle";
    public static String getADD = "ADD";
    public static String getEDIT = "EDIT";
    public static String getDELETE = "DELETE";
    public static String getPREVIEW = "PREVIEW";
    public  static String getStartDateString = "getStartDateString";
    public  static String getAppDateString = "getAppDateString";
    public  static String getCSNameSring = "getCSNameSring";
    public  static String getCScodeSring = "getCScodeSring";
    public  static String getEndDateString = "getEndDateString";

    public String getArrayAppointmentTime() {
        String str;
        String strArray = "";
        for (int i = 6; i <= 23; i += 1) {
            for (int l = 0; l <= 30; l += 30) {
                if (String.valueOf(i).length() < 2) {
                    if (String.valueOf(l).length() < 2) {
                        str = "0" + String.valueOf(i) + ":0" + String.valueOf(l);
                    } else {
                        str = "0" + String.valueOf(i) + ":" + String.valueOf(l);
                    }
                } else {
                    if (String.valueOf(l).length() < 2) {
                        str = String.valueOf(i) + ":0" + String.valueOf(l);
                    } else {
                        str = String.valueOf(i) + ":" + String.valueOf(l);
                    }
                }

                if (strArray.trim().equals("")) {
                    strArray = "[\"" + str + "\"";
                } else {
                    strArray = strArray + "," + "\"" + str + "\"";
                }
            }
        }
        strArray = strArray + "]";
        return  strArray;
    }//getArrayAppointmentTime

    //Convert Data Webservice To JSONArray Not [ ]
    public String JsonXmlToJsonStringNotSquareBracket(String string) {
        string = string.substring(40, string.length());
        string = string.replace("<string xmlns=\"http://58.181.171.23/Webservice/\">", "");
        string = string.replace("</string>", "");
        string = string.replace("[", "").replace("]", "");
        return string;
    }

    //Convert Data Webservice To JSONArray
    public String JsonXmlToJsonString(String string) {
        string = string.substring(40, string.length());
        string = string.replace("<string xmlns=\"http://58.181.171.23/Webservice/\">", "");
        string = string.replace("</string>", "");
        return string;
    }

    //Format Date integer To yyyy-MM-dd
    public String FormatDateyyyy_MM_dd_fromDateInteger(int intYear, int intMonth, int intDay) {
        String tag = "7SepV1";
        String resultString = null;

        String strYear = Integer.toString(intYear);
        String strMonth = Integer.toString(intMonth + 1);;
        if (strMonth.length() == 1) {
            strMonth = "0" + strMonth;
        }

        String strDay = Integer.toString(intDay);
        if (strDay.length() == 1) {
            strDay = "0" + strDay;
        }
        resultString = strYear + "-" + strMonth + "-" + strDay;
        Log.d(tag, "resultString ==> " + resultString);

        return resultString;
    }

    //Format Date of String dd/MM/yyyy from yyyy-MM-dd
    public String FormatDateddMMyyyy_fromStringYYYY_MM_DD(String s) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            Date test = simpleDateFormat.parse(s);
        } catch (ParseException pe) {
            //Date is invalid, try next format
            return null;
        }
        String strYear = s.substring(0, 4);
        String strMonth = s.substring(5, 7);
        String strDay = s.substring(8, 10);
        s = strDay + "/" + strMonth + "/" + strYear;
        return s;
    }

    //Format Date of String To dd/MM/yyyy
    public Date setConvertStringToDate(String s) {
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(s);
            return date;
        } catch (ParseException pe) {
            //Date is invalid, try next format
            return null;
        }
    }

    //Format Date of String yyyy-MM-dd from dd/MM/yyyy
    public String FormatStringDate_ddMMyyyy_To_yyyyMMdd(String strAppDate) {
        String resultString = null;
        String[] strings = strAppDate.split("/");
        resultString = strings[2] + "-" + strings[1] + "-" + strings[0];

        return resultString;
    }//myFormatAppDate

    //get Year int from yyyy-MM-dd
    public int getYear_fromStringYYYYMMDD(String strAppDate) {
        int resultInt = 0;
        String[] strings = strAppDate.split("-");
        resultInt =  Integer.parseInt(strings[0]);

        return resultInt;
    }//getYear_fromStringYYYYMMDD

    //get Month int from yyyy-MM-dd
    public int getMonth_fromStringYYYYMMDD(String strAppDate) {
        int resultInt = 0;
        String[] strings = strAppDate.split("-");
        resultInt =  Integer.parseInt(strings[1]);

        return resultInt;
    }//getMonth_fromStringYYYYMMDD

    //get Day int from yyyy-MM-dd
    public int getDay_fromStringYYYYMMDD(String strAppDate) {
        int resultInt = 0;
        String[] strings = strAppDate.split("-");
        String s = strings[2];
        resultInt =  Integer.parseInt(strings[2]);

        return resultInt;
    }//getDay_fromStringYYYYMMDD

    public boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public boolean isEmptyString(String s) {
        return s.toString().trim().length() == 0;
    }

    public GlobalVar() {
    }



    private static GlobalVar mInstance = null;

    public int someValueIWantToKeep;

//    protected GlobalVar(){}

    public static synchronized pneumax.websales.manager.GlobalVar getInstance() {
        if (null == mInstance) {
            mInstance = new GlobalVar();
        }
        return mInstance;
    }

}
