package com.azarnush.webeskan;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.models.ResidentPanel.Unit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewunitFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    View view;
    Spinner spinner3, spinner4;
    List<String> number_of_units = new ArrayList<>();
    List<String> type_of_ownership = new ArrayList<>();
    Button btn_unitRegister;
    String unit_title_select;
    String personType;
    int personType_number;
    int position_select;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newunit, container, false);
        spinner3 = view.findViewById(R.id.spinner3);
        spinner4 = view.findViewById(R.id.spinner4);
        btn_unitRegister = view.findViewById(R.id.btn_unitRegister);
        btn_unitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendJsonObjectRequest_post_unit();

            }
        });

        for (int i = 1; i <= GetBuildingCodeFragment.size_units; i++) {
            number_of_units.add("واحد " + i);
        }
        type_of_ownership.add("فقط مالک هستم");
        type_of_ownership.add("فقط ساکن هستم");
        type_of_ownership.add("ساکن و مالک هستم");

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, type_of_ownership);
        spinner4.setAdapter(dataAdapter4);
        spinner4.setOnItemSelectedListener(this);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, number_of_units);
        spinner3.setAdapter(dataAdapter3);
        spinner3.setOnItemSelectedListener(this);


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinner3:
                unit_title_select = parent.getItemAtPosition(position).toString();
                position_select = position;

                break;
            case R.id.spinner4:
                personType = parent.getItemAtPosition(position).toString();
                switch (personType) {
                    case "فقط مالک هستم":
                        personType_number = 1;
                        break;
                    case "فقط ساکن هستم":
                        personType_number = 2;
                        break;
                    case "ساکن و مالک هستم":
                        personType_number = 3;
                        break;
                }
                //  Toast.makeText(getContext(),unit_title_select,Toast.LENGTH_LONG);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void sendJsonObjectRequest_post_unit() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String user_id = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("reasonPhrase", "");
        int unitId = 0;
        try {
            unitId = GetBuildingCodeFragment.response_listUnits.getJSONObject(position_select).getInt("unitId");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject();
        try {
            object.put("userId", Integer.valueOf(user_id));
            object.put("personType", personType_number);
            object.put("buildingId", Integer.valueOf(GetBuildingCodeFragment.buildingRefId));
            object.put("unitRefId", unitId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://api.webeskan.com/api/v1/users/get-unit/";

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    Boolean isSuccessStatusCode = response.getBoolean("isSuccessStatusCode");
                    if (isSuccessStatusCode == false) {
                        Toast.makeText(getContext(), "این واحد قبلا ثبت شده است", Toast.LENGTH_LONG).show();
                    } else {
                        Resident_panelFragment.units.add(new Unit(false, ++Resident_panelFragment.i + "", GetBuildingCodeFragment.buildingTitle, unit_title_select, null));
                        Resident_panelFragment.adapter.notifyDataSetChanged();
                    }

                    NewunitFragment.this.dismiss();

                } catch (Exception e) {

                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, object, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

}