package pneumax.websales.manager;

import android.widget.EditText;

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
