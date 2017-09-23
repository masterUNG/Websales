package pneumax.websales.object;

import java.util.List;

/**
 * Created by sitrach on 21/09/2017.
 */

public class CustomerGrid {

    private List<CustomerGridBean> customerGridBeen;

    public List<CustomerGridBean> getCustomerGridBeen() {
        return customerGridBeen;
    }

    public void setCustomerGridBeen(List<CustomerGridBean> customerGridBeen) {
        this.customerGridBeen = customerGridBeen;
    }

    public static class CustomerGridBean {
        /**
         * Number1 : 1
         * CScode : 408646
         * CSName : กรีน ดิสทริบิวชั่น,ห้างหุ้นส่วนจำกัด
         * Areacode : 010
         * Areaname : พระโขนง/กรุงเทพฯ
         * CSICode : AAB
         * CSBCode : T
         * SAcode : 2102-1
         * DPcode : ACR
         */

        private int RowNo;
        private String CScode;
        private String CSName;
        private String Areacode;
        private String Areaname;
        private String CSICode;
        private String CSBCode;
        private String SAcode;
        private String DPcode;

        public int getRowNo() {
            return RowNo;
        }

        public void setRowNo(int RowNo) {
            this.RowNo = RowNo;
        }

        public String getCScode() {
            return CScode;
        }

        public void setCScode(String CScode) {
            this.CScode = CScode;
        }

        public String getCSName() {
            return CSName;
        }

        public void setCSName(String CSName) {
            this.CSName = CSName;
        }

        public String getAreacode() {
            return Areacode;
        }

        public void setAreacode(String Areacode) {
            this.Areacode = Areacode;
        }

        public String getAreaname() {
            return Areaname;
        }

        public void setAreaname(String Areaname) {
            this.Areaname = Areaname;
        }

        public String getCSICode() {
            return CSICode;
        }

        public void setCSICode(String CSICode) {
            this.CSICode = CSICode;
        }

        public String getCSBCode() {
            return CSBCode;
        }

        public void setCSBCode(String CSBCode) {
            this.CSBCode = CSBCode;
        }

        public String getSAcode() {
            return SAcode;
        }

        public void setSAcode(String SAcode) {
            this.SAcode = SAcode;
        }

        public String getDPcode() {
            return DPcode;
        }

        public void setDPcode(String DPcode) {
            this.DPcode = DPcode;
        }
    }
}
