package pneumax.websales.manager;

import android.widget.EditText;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by sitrach on 21/08/2017.
 */

public class GlobalVar {

    //Convert Data Webservice To JSONArray Not [ ]
    public String JsonXmlToJsonStringNotSquareBracket(String string){
        string = string.substring(40, string.length());
        string = string.replace("<string xmlns=\"http://58.181.171.23/Webservice/\">", "");
        string = string.replace("</string>", "");
        string = string.replace("[", "").replace("]","");
        return string;
    }

    //Convert Data Webservice To JSONArray
    public String JsonXmlToJsonString(String string){
        string = string.substring(40, string.length());
        string = string.replace("<string xmlns=\"http://58.181.171.23/Webservice/\">", "");
        string = string.replace("</string>", "");
        return string;
    }

    //Format Date of String To dd/MM/yyyy
    public String FormatDateOfString_ddMMyyyy(String s) {
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

    public GlobalVar() {
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

    public String FormatStringDate_ddMMyyyy_To_yyyyMMdd(String strAppDate) {
        String resultString = null;
        String[] strings = strAppDate.split("/");
        resultString = strings[2] + "-" + strings[1] + "-" + strings[0];

        return resultString;
    }//myFormatAppDate

    public boolean isEmptyEditText(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    public boolean isEmptyString(String s) {
        return s.toString().trim().length() == 0;
    }

    private static GlobalVar mInstance= null;

    public int someValueIWantToKeep;

//    protected GlobalVar(){}

    public static synchronized pneumax.websales.manager.GlobalVar getInstance(){
        if(null == mInstance){
            mInstance = new GlobalVar();
        }
        return mInstance;
    }

}
