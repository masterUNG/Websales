package pneumax.websales.manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import pneumax.websales.R;

/**
 * Created by sitrach on 22/09/2017.
 */

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private String[] csCodeStrings, csNameStrings, areaNameStrings;

    public CustomerAdapter(Context context,
                           String[] csCodeStrings,
                           String[] csNameStrings,
                           String[] areaNameStrings) {
        this.context = context;
        this.csCodeStrings = csCodeStrings;
        this.csNameStrings = csNameStrings;
        this.areaNameStrings = areaNameStrings;
    }


    @Override
    public int getCount() {
        return csCodeStrings.length;
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
        //ใส่ข้อมูลใน listview_customer
        View view1 = layoutInflater.inflate(R.layout.listview_customer, viewGroup, false);

        //Initial View
        TextView cscodeTextView = view1.findViewById(R.id.txtColCScode);
        TextView csnameTextView = view1.findViewById(R.id.txtColCSname);
        TextView areanameTextView = view1.findViewById(R.id.txtColAreaname);

        //Show View
        cscodeTextView.setText(csCodeStrings[i]);
        csnameTextView.setText(csNameStrings[i]);
        areanameTextView.setText(areaNameStrings[i]);

        return view1;
    }
}//Main Class
