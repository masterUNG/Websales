package pneumax.websales.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sitrach on 11/09/2017.
 */

public class ResultExecuteSQL implements Parcelable {

    public String ResultID;
    public String ResultMessage;

    public final static String TABLE_NAME = "ResultExecuteSQL";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ResultID);
        dest.writeString(this.ResultMessage);
    }

    public ResultExecuteSQL() {
    }

    protected ResultExecuteSQL(Parcel in) {
        this.ResultID = in.readString();
        this.ResultMessage = in.readString();
    }

    public static final Parcelable.Creator<ResultExecuteSQL> CREATOR = new Parcelable.Creator<ResultExecuteSQL>() {
        @Override
        public ResultExecuteSQL createFromParcel(Parcel source) {
            return new ResultExecuteSQL(source);
        }

        @Override
        public ResultExecuteSQL[] newArray(int size) {
            return new ResultExecuteSQL[size];
        }
    };
}
