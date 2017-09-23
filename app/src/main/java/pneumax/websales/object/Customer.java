package pneumax.websales.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sitrach on 21/09/2017.
 */

public class Customer implements Parcelable {

    public String CScode;
    public String CSname;
    public String CSBcode;
    public String CSIcode;
    public String AREANAME;
    public String SACODE;
    public String STFfname;
    public String DPcode;
    public String CSBdes;
    public String CSIdes;
    public final static String TABLE_NAME = "Customer";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.CScode);
        dest.writeString(this.CSname);
        dest.writeString(this.CSBcode);
        dest.writeString(this.CSIcode);
        dest.writeString(this.AREANAME);
        dest.writeString(this.SACODE);
        dest.writeString(this.STFfname);
        dest.writeString(this.DPcode);
        dest.writeString(this.CSBdes);
        dest.writeString(this.CSIdes);
    }

    public Customer() {
    }

    protected Customer(Parcel in) {
        this.CScode = in.readString();
        this.CSname = in.readString();
        this.CSBcode = in.readString();
        this.CSIcode = in.readString();
        this.AREANAME = in.readString();
        this.SACODE = in.readString();
        this.STFfname = in.readString();
        this.DPcode = in.readString();
        this.CSBdes = in.readString();
        this.CSIdes = in.readString();
    }

    public static final Parcelable.Creator<Customer> CREATOR = new Parcelable.Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
