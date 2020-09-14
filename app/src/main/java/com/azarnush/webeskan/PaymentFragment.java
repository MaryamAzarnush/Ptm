package com.azarnush.webeskan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.azarnush.webeskan.models.ResidentPanel.Debt;

import com.azarnush.webeskan.models.ResidentPanel.ShebaNumber;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class PaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    Spinner spinner1, spinner2;
    List<String> payment_type = new ArrayList<>();
    List<String> sheba_numbers = new ArrayList<>();
    List<ShebaNumber> shebaNumbers_and_ids = new ArrayList<>();
    TextView txt_sum_pay, txt_show_debts;
    int debt_checked = 0;
    Button btn_pay;
    int shebaNumberIdSelect;
    String url_pay;
    ArrayAdapter<String> dataAdapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        txt_sum_pay = view.findViewById(R.id.txt_sum_pay);
        txt_show_debts = view.findViewById(R.id.txt_show_debts);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        btn_pay = view.findViewById(R.id.btn_pay);
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendJSONObjectRequest_paydebt();


            }
        });

        txt_sum_pay.setText(Resident_boardFragment.sum + " تومان");

        RealmResults<Debt> results = Resident_boardFragment.realm.where(Debt.class).findAll();
        for (int i = 0; i < results.size(); i++) {
            if (results.get(i).isCheckDebt()) {
                debt_checked++;
            }
        }
        String show_debts = "";
        for (int i = 0; i < Resident_boardFragment.debtListChecked.size(); i++) {
            show_debts = show_debts + Resident_boardFragment.debtListChecked.get(i).debtTitle + " ";
            if (Resident_boardFragment.debtListChecked.size() - 1 != i) {
                show_debts = show_debts + ", ";
            }

        }
        txt_show_debts.setText(show_debts);

        Resident_boardFragment.realm.beginTransaction();
        results.deleteAllFromRealm();
        Resident_boardFragment.realm.commitTransaction();

        sendJSONArrayRequest_getShebaNumbers();


        payment_type.add("پرداخت آنلاین");
//        sheba_numbers.add("شماره شبا");
//        sheba_numbers.add()
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, payment_type);
        dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sheba_numbers);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(getContext(), item, Toast.LENGTH_LONG).show();
        for (int j = 0; j < shebaNumbers_and_ids.size(); j++) {
            if (item == shebaNumbers_and_ids.get(j).getShebaNumber()) {
                shebaNumberIdSelect = shebaNumbers_and_ids.get(j).getShebaNumberId();
            }
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void sendJSONArrayRequest_getShebaNumbers() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/residence/get-sheba-numbers/" + UnitsAdapter.residenceRefId;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jo = response.getJSONObject(i);
                        String shebaNumber = jo.getString("shebaNumber");
                        int shebaNumberId = jo.getInt("shebaNumberId");
                        sheba_numbers.add(shebaNumber);
                        ShebaNumber shebaNumber_and_id = new ShebaNumber(shebaNumberId, shebaNumber);
                        shebaNumbers_and_ids.add(shebaNumber_and_id);

                    }
                    dataAdapter2.notifyDataSetChanged();


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

    public void sendJSONObjectRequest_paydebt() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/residence/paydebt";

        JSONObject object = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < Resident_boardFragment.debtListChecked.size(); i++) {
                Debt debt = Resident_boardFragment.debtListChecked.get(i);
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("checkedDebt", debt.checkDebt);
                jsonObject.put("actionType", debt.actionType);
                jsonObject.put("debtId", debt.debtId);
                jsonObject.put("payableDebtAmount", debt.payableDebtAmount);
                jsonObject.put("debtAmount", debt.debtAmount);
                jsonObject.put("debtDate", debt.debtDate);
                jsonObject.put("debtTitle", debt.debtTitle);
                jsonObject.put("residenceId", UnitsAdapter.residenceRefId);

                jsonArray.put(jsonObject);

            }
            object.put("residenceDebtList", jsonArray);
            object.put("residenceId", UnitsAdapter.residenceRefId);
            if (shebaNumberIdSelect == 0) {
                Toast.makeText(getContext(), "لطفا شماره شبا را انتخاب نمایید", Toast.LENGTH_LONG).show();
                return;
            }
            object.put("shebaNumberRefId", shebaNumberIdSelect);
            object.put("userId", Resident_panelFragment.user_id);
        } catch (Exception e) {
            // Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                    url_pay = response.getString("reasonPhrase");
                    //Toast.makeText(getContext(), url_pay, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url_pay));
                    startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.i("ptm", error.getStackTrace().clone().toString());

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

}