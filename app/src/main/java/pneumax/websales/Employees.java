package pneumax.websales;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sitrach on 02/09/2017.
 */

class Employees implements Parcelable {

    public String STFcode;
    public String STFtitle;
    public String DPcode;
    public String DPname;
    public String PSTdes_Eng;
    public String PSTCode;
    public String SACode;
    public String STFfname;
    public String STFlname;
    public String STFfullname;
    public String BRcode1;
    public String BRdesc_T;
    public String STFstart;

    public final static String TABLE_NAME = "Employees";
//    public final static String COLUMN_STFCODE = "STFCODE";
//    public final static String COLUMN_STFFULLNAME = "STFFULLNAME";
//    public final static String COLUMN_DPCODE = "DPCODE";
//    public final static String COLUMN_BRCODE = "BRCODE";
//    public final static String COLUMN_STFSTART = "START";
//    public final static String COLUMN_STFEND = "STFEND";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.STFcode);
        dest.writeString(this.STFtitle);
        dest.writeValue(this.DPcode);
        dest.writeValue(this.DPname);
        dest.writeValue(this.STFstart);
        dest.writeValue(this.PSTdes_Eng);
        dest.writeValue(this.PSTCode);
        dest.writeValue(this.SACode);
        dest.writeValue(this.STFfname);
        dest.writeValue(this.STFlname);
        dest.writeValue(this.STFfullname);
        dest.writeValue(this.BRcode1);
        dest.writeValue(this.BRdesc_T);
        dest.writeValue(this.STFstart);
    }

    public Employees() {
    }

    protected Employees(Parcel in) {
        this.STFcode = in.readString();
        this.STFtitle = in.readString();
        this.DPcode = in.readString();
        this.DPname = in.readString();
        this.STFstart = in.readString();
        this.PSTdes_Eng = in.readString();
        this.PSTCode = in.readString();
        this.SACode = in.readString();
        this.STFfname = in.readString();
        this.STFlname = in.readString();
        this.STFfullname = in.readString();
        this.BRcode1 = in.readString();
        this.BRdesc_T = in.readString();
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
