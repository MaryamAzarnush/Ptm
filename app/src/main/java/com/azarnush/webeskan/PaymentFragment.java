package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.ResidentPanel.UnitsAdapter;
import com.azarnush.webeskan.models.Laws.LawInfo2;
import com.azarnush.webeskan.models.ResidentPanel.Debt;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class PaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    Spinner spinner1, spinner2;
    List<String> payment_type = new ArrayList<>();
    List<String> shaba_number = new ArrayList<>();
    TextView txt_sum_pay, txt_show_debts;
    int debt_checked = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        txt_sum_pay = view.findViewById(R.id.txt_sum_pay);
        txt_show_debts = view.findViewById(R.id.txt_show_debts);
        txt_sum_pay.setText(Resident_boardFragment.sum + " تومان");

        RealmResults<Debt> results = Resident_boardFragment.realm.where(Debt.class).findAll();
        for (int i = 0; i <results.size() ; i++) {
            if(results.get(i).isCheckDebt()){
                debt_checked++;
            }
        }
        String show_debts = "";
        for (int i = 0; i < results.size(); i++) {
            Debt debt = results.get(i);
            Boolean isCheckDebt = debt.isCheckDebt();
            if (isCheckDebt) {
                if((results.size()-1)==i || results.size()!=debt_checked){
                    show_debts = show_debts + debt.debtTitle;
                }else {
                    show_debts = show_debts + debt.debtTitle + " , ";
                }

            }
        }
        txt_show_debts.setText(show_debts);

        Resident_boardFragment.realm.beginTransaction();
        results.deleteAllFromRealm();
        Resident_boardFragment.realm.commitTransaction();

        payment_type.add("پرداخت آنلاین");
        shaba_number.add("get Shaba number");
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, payment_type);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, shaba_number);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        sendJSONArrayRequest_getShabaNumbers();

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void sendJSONArrayRequest_getShabaNumbers() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/residence/get-sheba-numbers/" + UnitsAdapter.residenceRefId;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

}