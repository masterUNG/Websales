package pneumax.websales.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sitrach on 05/09/2017.
 */

public class ObjectSale implements Parcelable {

    public String STFcode;
    public String DPcode;
    public String DPname;
    public String PSTCode;
    public String SACode;
    public String STFfullname;
    public String SAJobDesc;

    public final static String TABLE_NAME = "SalesTB";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.STFcode);
         dest.writeString(this.DPcode);
        dest.writeString(this.DPname);
        dest.writeString(this.PSTCode);
        dest.writeString(this.SACode);
        dest.writeString(this.STFfullname);
        dest.writeString(this.SAJobDesc);
    }

    public ObjectSale() {
    }

    protected ObjectSale(Parcel in) {
        this.STFcode = in.readString();
        this.DPcode = in.readString();
        this.DPname = in.readString();
        this.PSTCode = in.readString();
        this.SACode = in.readString();
        this.STFfullname = in.readString();
        this.SAJobDesc = in.readString();
    }

    public static final Parcelable.Creator<ObjectSale> CREATOR = new Parcelable.Creator<ObjectSale>() {
        @Override
        public ObjectSale createFromParcel(Parcel source) {
            return new ObjectSale(source);
        }

        @Override
        public ObjectSale[] newArray(int size) {
            return new ObjectSale[size];
        }
    };
}
