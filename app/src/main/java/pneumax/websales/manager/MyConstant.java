package pneumax.websales.manager;

/**
 * Created by Sitrach on 04/09/2017.
 */

public class MyConstant {

    //About URL getLogin
    private String urlGetLoginWhere = "http://58.181.171.23/webservice/Service.asmx/getLogin";

    //กด Alt+Ins เลือก GETTER มันจะสร้างให้อัตโนมัติ
    public String getUrlGetLoginWhere() {
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

    //About URL getCustomer
    private String urlGetCustomer = "http://58.181.171.23/webservice/Service.asmx/getCustomer";

    public String getUrlGetCustomer() {
        return urlGetCustomer;
    }

    //About URL getCustomerGrid
    private String urlGetCustomerGrid = "http://58.181.171.23/webservice/Service.asmx/getCustomerGrid";

    public String getUrlGetCustomerGrid() {
        return urlGetCustomerGrid;
    }

    //About URL DeleteAppointment
    private String urlDeleteAppointment = "http://58.181.171.23/webservice/Service.asmx/DeleteAppointment";

    public String getUrlDeleteAppointment() {
        return urlDeleteAppointment;
    }

    //About URL UpdateAppointment
    private String urlUpdateAppointment = "http://58.181.171.23/webservice/Service.asmx/UpdateAppointment";

    public String getUrlUpdateAppointment() {
        return urlUpdateAppointment;
    }

    //About URL InsertAppointment
    private String urlInsertAppointment = "http://58.181.171.23/webservice/Service.asmx/InsertAppointment";

    public String getUrlInsertAppointment() {
        return urlInsertAppointment;
    }

    //About URL getContactPersonCombobox
    private String urlGetContactPersonCombobox = "http://58.181.171.23/webservice/Service.asmx/getContactPersonCombobox";
    public String getUrlGetContactPersonCombobox() {
        return urlGetContactPersonCombobox;
    }

    //About URL getAppointmentWorkType
    private String urlGetAppointmentWorkType = "http://58.181.171.23/webservice/Service.asmx/getAppointmentWorkType";

    public String getUrlGetAppointmentWorkType() {
        return urlGetAppointmentWorkType;
    }

    //About URL getAppointmentPurpose
    private String urlGetAppointmentPurpose = "http://58.181.171.23/webservice/Service.asmx/getAppointmentPurpose";

    public String getUrlGetAppointmentPurpose() {
        return urlGetAppointmentPurpose;
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

    private String startDateString = "2017-08-01";

    public String getStartDateString() {
        return startDateString;
    }

    private String endDateString = "2017-09-30";

    public String getEndDateString() {
        return endDateString;
    }

}// Main Class
