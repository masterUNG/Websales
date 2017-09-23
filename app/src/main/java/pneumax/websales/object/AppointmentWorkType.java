package pneumax.websales.object;


import java.util.List;

/**
 * Created by sitrach on 20/09/2017.
 */

public class AppointmentWorkType {

    private List<AppointmentWorkTypeBean> appointmentWorkTypeBeen;

    public List<AppointmentWorkTypeBean> getAppointmentWorkTypeBeen() {
        return appointmentWorkTypeBeen;
    }

    public void setAppointmentWorkTypeBeen(List<AppointmentWorkTypeBean> appointmentWorkTypeBeen) {
        this.appointmentWorkTypeBeen = appointmentWorkTypeBeen;
    }

    public static class AppointmentWorkTypeBean {

        /**
         * WTcode : 0001
         * WTname : งานโปรเจ็ค
         * WTactive : Y
         * WTVisitType : V,P
         */

        private String WTcode;
        private String WTname;
        private String WTactive;
        private String WTVisitType;

        public String getWTcode() {
            return WTcode;
        }

        public void setWTcode(String WTcode) {
            this.WTcode = WTcode;
        }

        public String getWTname() {
            return WTname;
        }

        public void setWTname(String WTname) {
            this.WTname = WTname;
        }

        public String getWTactive() {
            return WTactive;
        }

        public void setWTactive(String WTactive) {
            this.WTactive = WTactive;
        }

        public String getWTVisitType() {
            return WTVisitType;
        }

        public void setWTVisitType(String WTVisitType) {
            this.WTVisitType = WTVisitType;
        }
    }
}

