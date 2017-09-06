package pneumax.websales.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import pneumax.websales.R;

/**
 * Created by Sitrach on 06/09/2017.
 */

public class AppointmentAdapter extends BaseAdapter {
    private Context context;
    private String[] dateAppointStrings, timeAppointStrings, csnameAppointStrings;

    public AppointmentAdapter(Context context,
                              String[] dateAppointStrings,
                              String[] timeAppointStrings,
                              String[] csnameAppointStrings) {
        this.context = context;
        this.dateAppointStrings = dateAppointStrings;
        this.timeAppointStrings = timeAppointStrings;
        this.csnameAppointStrings = csnameAppointStrings;
    }

    @Override
    public int getCount() {
        return csnameAppointStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //LayoutInflater ป้องกันการ Error อาจเกิดจาก String + integer แล้ว error
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //ใส่ข้อมูลใน listview_appointment
        View view1 = layoutInflater.inflate(R.layout.listview_appointment, viewGroup, false);

        //Initial View
        TextView dateTextView = view1.findViewById(R.id.txtColAppdate);
        TextView timeTextView = view1.findViewById(R.id.txtColApptime);
        TextView csnameTextView = view1.findViewById(R.id.txtColCsname);

        //Show View
        dateTextView.setText(dateAppointStrings[i]);
        timeTextView.setText(timeAppointStrings[i]);
        csnameTextView.setText(csnameAppointStrings[i]);

        return view1;
    }
}//Main Class
