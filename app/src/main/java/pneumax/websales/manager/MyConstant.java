package pneumax.websales.manager;

/**
 * Created by Sitrach on 04/09/2017.
 */

public class MyConstant {


    //About URL
    private String urlGetSalesNameWhere = "http://58.181.171.23/webservice/Service.asmx/getEmployeeName";
    //กด Alt+Ins เลือก GETTER มันจะสร้างให้อัตโนมัติ
    public String getUrlGetSalesNameWhere() {
        return urlGetSalesNameWhere;
    }

    private String[] columnEmployeesStrings = new String[]{
            "STFcode",
            "STFtitle",
            "DPcode",
            "DPname",
            "PSTdes_Eng",
            "PSTCode",
            "SACode",
            "STFfname",
            "STFlname",
            "STFfullname",
            "BRcode1",
            "BRdesc_T",
            "STFstart"};

    public String[] getColumnEmployeesStrings() {
        return columnEmployeesStrings;
    }
}// Main Class
