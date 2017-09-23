package pneumax.websales.object;

import java.util.List;

/**
 * Created by sitrach on 20/09/2017.
 */

public class AppointmentPurpose {

    private List<AppointmentPurposeBean> appointmentPurposeBeen;

    public List<AppointmentPurposeBean> getAppointmentPurposeBeen() {
        return appointmentPurposeBeen;
    }

    public void setAppointmentPurposeBeen(List<AppointmentPurposeBean> appointmentPurposeBeen) {
        this.appointmentPurposeBeen = appointmentPurposeBeen;
    }

    public static class AppointmentPurposeBean {

        /**
         * PURPcode : 0001
         * PURPName : ลูกค้าใหม่
         * PURPactive : Y
         */

        private String PURPcode;
        private String PURPName;
        private String PURPactive;

        public String getPURPcode() {
            return PURPcode;
        }

        public void setPURPcode(String PURPcode) {
            this.PURPcode = PURPcode;
        }

        public String getPURPName() {
            return PURPName;
        }

        public void setPURPName(String PURPName) {
            this.PURPName = PURPName;
        }

        public String getPURPactive() {
            return PURPactive;
        }

        public void setPURPactive(String PURPactive) {
            this.PURPactive = PURPactive;
        }
    }
}
