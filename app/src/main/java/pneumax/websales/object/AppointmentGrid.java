package pneumax.websales.object;

import java.util.List;

/**
 * Created by Sitrach on 05/09/2017.
 */

public class AppointmentGrid {

    /**
     * Number1 : 1
     * AppDate : 2017-09-05 00:00:00
     * AppStartTime : 13:30
     * CSCode : 139961
     * CSthiname : ไอซิน ทาคาโอก้า ฟาวน์ดริ บางปะกง จำกัด
     * CTPname : คุณปราโมทย์
     * PURPName : ลูกค้าใหม่
     * WTname : งานโปรเจ็ค
     * Remark : แนะนำตัว
     * AppReasonReturn :
     * AreaName : นิคมฯอมตะนคร 2 /พานทอง
     * CSIcode : ATP
     * CSBdes : End User
     * SAcode : 2873-0
     * DPCode : PNE
     * WTcode : 0001
     * PURPcode : 0001
     * CreateDate : 2017-09-04 22:50:24
     * AppVisit_ByPhone : V
     */

    private List<AppointmentGridBean> appointmentgrids;

    public List<AppointmentGridBean> getAppointmentgrids() {
        return appointmentgrids;
    }

    public void setAppointmentgrids(List<AppointmentGridBean> appointmentgrids) {
        this.appointmentgrids = appointmentgrids;
    }

    public static class AppointmentGridBean {

        private int Number1;
        private String AppDate;
        private String AppStartTime;
        private String CSCode;
        private String CSthiname;
        private String CTPname;
        private String PURPName;
        private String WTname;
        private String Remark;
        private String AppReasonReturn;
        private String AreaName;
        private String CSIcode;
        private String CSBdes;
        private String SAcode;
        private String DPCode;
        private String WTcode;
        private String PURPcode;
        private String CreateDate;
        private String AppVisit_ByPhone;

        public int getNumber1() {
            return Number1;
        }

        public void setNumber1(int Number1) {
            this.Number1 = Number1;
        }

        public String getAppDate() {
            return AppDate;
        }

        public void setAppDate(String AppDate) {
            this.AppDate = AppDate;
        }

        public String getAppStartTime() {
            return AppStartTime;
        }

        public void setAppStartTime(String AppStartTime) {
            this.AppStartTime = AppStartTime;
        }

        public String getCSCode() {
            return CSCode;
        }

        public void setCSCode(String CSCode) {
            this.CSCode = CSCode;
        }

        public String getCSthiname() {
            return CSthiname;
        }

        public void setCSthiname(String CSthiname) {
            this.CSthiname = CSthiname;
        }

        public String getCTPname() {
            return CTPname;
        }

        public void setCTPname(String CTPname) {
            this.CTPname = CTPname;
        }

        public String getPURPName() {
            return PURPName;
        }

        public void setPURPName(String PURPName) {
            this.PURPName = PURPName;
        }

        public String getWTname() {
            return WTname;
        }

        public void setWTname(String WTname) {
            this.WTname = WTname;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getAppReasonReturn() {
            return AppReasonReturn;
        }

        public void setAppReasonReturn(String AppReasonReturn) {
            this.AppReasonReturn = AppReasonReturn;
        }

        public String getAreaName() {
            return AreaName;
        }

        public void setAreaName(String AreaName) {
            this.AreaName = AreaName;
        }

        public String getCSIcode() {
            return CSIcode;
        }

        public void setCSIcode(String CSIcode) {
            this.CSIcode = CSIcode;
        }

        public String getCSBdes() {
            return CSBdes;
        }

        public void setCSBdes(String CSBdes) {
            this.CSBdes = CSBdes;
        }

        public String getSAcode() {
            return SAcode;
        }

        public void setSAcode(String SAcode) {
            this.SAcode = SAcode;
        }

        public String getDPCode() {
            return DPCode;
        }

        public void setDPCode(String DPCode) {
            this.DPCode = DPCode;
        }

        public String getWTcode() {
            return WTcode;
        }

        public void setWTcode(String WTcode) {
            this.WTcode = WTcode;
        }

        public String getPURPcode() {
            return PURPcode;
        }

        public void setPURPcode(String PURPcode) {
            this.PURPcode = PURPcode;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getAppVisit_ByPhone() {
            return AppVisit_ByPhone;
        }

        public void setAppVisit_ByPhone(String AppVisit_ByPhone) {
            this.AppVisit_ByPhone = AppVisit_ByPhone;
        }

    }
}
