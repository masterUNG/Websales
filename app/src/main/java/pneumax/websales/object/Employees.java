package pneumax.websales.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sitrach on 02/09/2017.
 */

public class Employees implements Parcelable {

    public String STFcode;
    public String STFtitle;
    public String DPcode;
    public String DPname;
    public String PSTdesEng;
    public String PSTCode;
    public String SACode;
    public String STFfname;
    public String STFlname;
    public String STFfullname;
    public String BRcode;
    public String BRdescThai;
    public String STFstart;

    public final static String TABLE_NAME = "Employees";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.STFcode);
        dest.writeString(this.STFtitle);
        dest.writeString(this.DPcode);
        dest.writeString(this.DPname);
        dest.writeString(this.PSTdesEng);
        dest.writeString(this.PSTCode);
        dest.writeString(this.SACode);
        dest.writeString(this.STFfname);
        dest.writeString(this.STFlname);
        dest.writeString(this.STFfullname);
        dest.writeString(this.BRcode);
        dest.writeString(this.BRdescThai);
        dest.writeString(this.STFstart);
    }

    public Employees() {
    }

    protected Employees(Parcel in) {
        this.STFcode = in.readString();
        this.STFtitle = in.readString();
        this.DPcode = in.readString();
        this.DPname = in.readString();
        this.PSTdesEng = in.readString();
        this.PSTCode = in.readString();
        this.SACode = in.readString();
        this.STFfname = in.readString();
        this.STFlname = in.readString();
        this.STFfullname = in.readString();
        this.BRcode = in.readString();
        this.BRdescThai = in.readString();
        this.STFstart = in.readString();
    }

    public static final Parcelable.Creator<Employees> CREATOR = new Parcelable.Creator<Employees>() {
        @Override
        public Employees createFromParcel(Parcel source) {
            return new Employees(source);
        }

        @Override
        public Employees[] newArray(int size) {
            return new Employees[size];
        }
    };
}
