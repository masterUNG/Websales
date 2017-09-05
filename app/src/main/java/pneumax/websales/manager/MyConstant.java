package pneumax.websales.manager;

/**
 * Created by Sitrach on 04/09/2017.
 */

public class MyConstant {

    //About URL getLogin
    private String urlGetLoginWhere = "http://58.181.171.23/webservice/Service.asmx/getLogin";
    //กด Alt+Ins เลือก GETTER มันจะสร้างให้อัตโนมัติ
    public String geturlGetLoginWhere() {
        return urlGetLoginWhere;
    }

    //About URL getEmployeeName
    private String urlGetSalesNameWhere = "http://58.181.171.23/webservice/Service.asmx/getEmployeeName";
    public String getUrlGetSalesNameWhere() {
        return urlGetSalesNameWhere;
    }

    //About URL getDepartment
    private String urlGetDepartmentWhere = "http://58.181.171.23/webservice/Service.asmx/getDepartment";
    public String getUrlGetDepartmentWhere() {
        return urlGetDepartmentWhere;
    }

    //About URL getSalesCode
    private String urlGetSalesCodeWhere = "http://58.181.171.23/webservice/Service.asmx/getSalesCode";
    public String getUrlGetSalesCodeWhere() {
        return urlGetSalesCodeWhere;
    }

    //About URL getAppointmentGrid
    private String urlGetAppointmentGrid = "http://58.181.171.23/webservice/Service.asmx/getAppointmentGrid";
    public String getUrlGetAppointmentGrid() {
        return urlGetAppointmentGrid;
    }

    //About URL getAppointment
    private String urlGetAppointment = "http://58.181.171.23/webservice/Service.asmx/getAppointment";
    public String getUrlGetAppointment() {
        return urlGetAppointment;
    }

    //About URL getCustomerGrid
    private String urlGetCustomerGrid = "http://58.181.171.23/webservice/Service.asmx/getCustomerGrid";
    public String getUrlGetCustomerGrid() {
        return urlGetCustomerGrid;
    }


    private String[] columnEmployeesStrings = new String[]{
            "STFcode",
            "STFtitle",
            "DPcode",
            "DPname",
            "PSTdesEng",
            "PSTCode",
            "SACode",
            "STFfname",
            "STFlname",
            "STFfullname",
            "BRcode",
            "BRdescThai",
            "STFstart"};

    public String[] getColumnEmployeesStrings() {
        return columnEmployeesStrings;
    }

}// Main Class
