package pneumax.websales.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sitrach on 05/09/2017.
 */

public class Appointment implements Parcelable{

    public String Number1;
    public String AppDate;
    public String AppStartTime;
    public String CSCode;
    public String CSthiname;
    public String CTPcode;
    public String CTPname;
    public String PURPName;
    public String WTname;
    public String Remark;
    public String AppReasonReturn;
    public String AreaName;
    public String CSIcode;
    public String CSBdes;
    public String SAcode;
    public String DPCode;
    public String WTcode;
    public String PURPcode;
    public String CreateDate;
    public String AppVisit_ByPhone;

    public final static String TABLE_NAME = "Employees";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Number1);
        dest.writeString(this.AppDate);
        dest.writeString(this.AppStartTime);
        dest.writeString(this.CSCode);
        dest.writeString(this.CSthiname);
        dest.writeString(this.CTPcode);
        dest.writeString(this.CTPname);
        dest.writeString(this.PURPName);
        dest.writeString(this.WTname);
        dest.writeString(this.Remark);
        dest.writeString(this.AppReasonReturn);
        dest.writeString(this.AreaName);
        dest.writeString(this.CSIcode);
        dest.writeString(this.CSBdes);
        dest.writeString(this.SAcode);
        dest.writeString(this.DPCode);
        dest.writeString(this.WTcode);
        dest.writeString(this.PURPcode);
        dest.writeString(this.CreateDate);
        dest.writeString(this.AppVisit_ByPhone);
    }

    public Appointment() {
    }

    protected Appointment(Parcel in) {
        this.Number1 = in.readString();
        this.AppDate = in.readString();
        this.AppStartTime = in.readString();
        this.CSCode = in.readString();
        this.CSthiname = in.readString();
        this.CTPcode = in.readString();
        this.CTPname = in.readString();
        this.PURPName = in.readString();
        this.WTname = in.readString();
        this.Remark = in.readString();
        this.AppReasonReturn = in.readString();
        this.AreaName = in.readString();
        this.CSIcode = in.readString();
        this.CSBdes = in.readString();
        this.SAcode = in.readString();
        this.WTcode = in.readString();
        this.PURPcode = in.readString();
        this.CreateDate = in.readString();
        this.AppVisit_ByPhone = in.readString();
    }

    public static final Parcelable.Creator<Appointment> CREATOR = new Parcelable.Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel source) {
            return new Appointment(source);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
        }
    };
}
