package pneumax.websales.object;

import java.util.List;

/**
 * Created by sitrach on 20/09/2017.
 */

public class ContactPerson {

    private List<ContactPersonBean> contactPersonBeen;

    public List<ContactPersonBean> getContactPersonBeen() {
        return contactPersonBeen;
    }

    public void setContactPersonBeen(List<ContactPersonBean> contactPersonBeen) {
        this.contactPersonBeen = contactPersonBeen;
    }

    public static class ContactPersonBean {

        /**
         * CTPcode : 226712-1
         * CTPname : คุณนิวัฒน์ นิลเปล่งแสง
         * CSCode : 226712
         */

        private String CTPcode;
        private String CTPname;
        private String CSCode;

        public String getCTPcode() {
            return CTPcode;
        }

        public void setCTPcode(String CTPcode) {
            this.CTPcode = CTPcode;
        }

        public String getCTPname() {
            return CTPname;
        }

        public void setCTPname(String CTPname) {
            this.CTPname = CTPname;
        }

        public String getCSCode() {
            return CSCode;
        }

        public void setCSCode(String CSCode) {
            this.CSCode = CSCode;
        }
    }
}
