package pneumax.websales;

/**
 * Created by sitrach on 16/08/2017.
 */

public class Employee {

    /**
     * STFcode : 2035
     * STFfullname : สิทธิ์ราช เรืองสมานไมตรี
     * DPcode : MIS
     * BRcode : BKK
     * STFstart : /Date(1235840400000)/
     * STFend : null
     */

    private String STFcode;
    private String STFfullname;
    private String DPcode;
    private String BRcode;
    private String STFstart;
    private Object STFend;

    public String getSTFcode() {
        return STFcode;
    }

    public void setSTFcode(String STFcode) {
        this.STFcode = STFcode;
    }

    public String getSTFfullname() {
        return STFfullname;
    }

    public void setSTFfullname(String STFfullname) {
        this.STFfullname = STFfullname;
    }

    public String getDPcode() {
        return DPcode;
    }

    public void setDPcode(String DPcode) {
        this.DPcode = DPcode;
    }

    public String getBRcode() {
        return BRcode;
    }

    public void setBRcode(String BRcode) {
        this.BRcode = BRcode;
    }

    public String getSTFstart() {
        return STFstart;
    }

    public void setSTFstart(String STFstart) {
        this.STFstart = STFstart;
    }

    public Object getSTFend() {
        return STFend;
    }

    public void setSTFend(Object STFend) {
        this.STFend = STFend;
    }
}
