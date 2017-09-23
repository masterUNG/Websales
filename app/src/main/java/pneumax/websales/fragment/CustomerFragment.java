package pneumax.websales.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import pneumax.websales.R;
import pneumax.websales.SuccessActivity;
import pneumax.websales.connected.GetCustomerGridWhere;
import pneumax.websales.manager.CustomerAdapter;
import pneumax.websales.manager.GlobalVar;
import pneumax.websales.manager.MyConstant;
import pneumax.websales.object.ObjectSale;

/**
 * Created by sitrach on 22/09/2017.
 */

public class CustomerFragment extends Fragment {

    private ObjectSale objectSaleLogin;
    private GlobalVar globalVar;
    private MyConstant myConstant;
    //Explicit
    private String searchCustName;
    private EditText vtxtSearchCSName;
    private Button vbtnSearchCSname;

    public static CustomerFragment customerFragment(Parcelable parcelObjectSaleLogin,
                                                    String strSearchCSname) {
        CustomerFragment customerFragment = new CustomerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ObjectSale.TABLE_NAME, parcelObjectSaleLogin);
        bundle.putString(GlobalVar.getInstance().getCSNameSring, strSearchCSname);
        customerFragment.setArguments(bundle);
        return customerFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objectSaleLogin = (ObjectSale) getArguments().getParcelable(ObjectSale.TABLE_NAME);
        globalVar = new GlobalVar();
        myConstant = new MyConstant();

        this.searchCustName = getArguments().getString(GlobalVar.getInstance().getCSNameSring);
        ;
        if (savedInstanceState != null) {
            searchCustName = savedInstanceState.getString(globalVar.getCSNameSring);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(globalVar.getCSNameSring, vtxtSearchCSName.getText().toString());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vtxtSearchCSName = getView().findViewById(R.id.txtSearchCSName);
        vtxtSearchCSName.setText(searchCustName);
        vbtnSearchCSname = getView().findViewById(R.id.btnSearchCSname);

        //Create ListView
        createListView();
        //Search Controller
        searchController();
        //back fragment
        backFragment();
    }

    private void backFragment() {
        TextView mtxtviewBack = getView().findViewById(R.id.txtviewBackCust);
        mtxtviewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }//backFragment

    private void searchController() {
        Button button = getView().findViewById(R.id.btnSearchCSname);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (vtxtSearchCSName.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "กรุณาป้อนชื่อลูกค้า บางส่วนด้วย !!!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (vtxtSearchCSName.getText().toString().length() < 3) {
                    Toast.makeText(getActivity(), "กรุณาป้อนชื่อลูกค้า อย่างน้อย 3 ตัวอักษร !!!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    searchCustName = vtxtSearchCSName.getText().toString();
                    //Create ListView
                    createListView();
                }
            }
        });
    }//Search Controller

    private void createListView() {
        ListView listView = getView().findViewById(R.id.livCustomer);
        String tag = "22SepV1";

        try {
            GetCustomerGridWhere getCustomerGridWhere = new GetCustomerGridWhere(getActivity());
            getCustomerGridWhere.execute(
                    objectSaleLogin.SACode,
                    objectSaleLogin.DPcode,
                    searchCustName, myConstant.getUrlGetCustomerGrid());

            String resultJSON = getCustomerGridWhere.get();
            resultJSON = globalVar.JsonXmlToJsonString(resultJSON);
            Log.d(tag, "JSON ==> " + resultJSON);

            JSONArray jsonArray = new JSONArray(resultJSON);
            //Array Data Customer To ListView
            final String[] CScodeStrings = new String[jsonArray.length()];
            final String[] CSnameStrings = new String[jsonArray.length()];
            String[] AreaNameStrings = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i += 1) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CScodeStrings[i] = jsonObject.getString("CScode");
                CSnameStrings[i] = jsonObject.getString("CSName");
                AreaNameStrings[i] = jsonObject.getString("Areaname");

                Log.d(tag, "CScode [" + i + "] ==> " + CScodeStrings[i]);
            }//for

            CustomerAdapter customerAdapter = new CustomerAdapter(getActivity(),
                    CScodeStrings, CSnameStrings, AreaNameStrings);
            listView.setAdapter(customerAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Click Item in Row ListView
                    confirmDialog(CScodeStrings[i]);
                }
            });

        } catch (Exception e) {
            Log.d(tag, "e Create ListView ==> " + e.toString());
        }
    }//createListView


    private void confirmDialog(final String cscodeString) {
        final String tag = "7SepV2";
        CharSequence[] charSequences = new CharSequence[]{"OK", "Cancel"};
        final int[] selectedChoice = {0};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false); //ถ้าไม่มีการเลือก false คือไม่ต้องทำอะไร
        builder.setIcon(android.R.drawable.ic_dialog_info); //Icon Dialog
        builder.setTitle("Do you want to customer ?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); //Close ตัวเอง
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(tag, "Choose ==> " + cscodeString);
                // Set Return Value CScode in SuccessActivity
                ((SuccessActivity) getActivity()).setReturnValueFragment(cscodeString);

                getActivity().getSupportFragmentManager().popBackStack();
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }//ConfirmDialog

}
