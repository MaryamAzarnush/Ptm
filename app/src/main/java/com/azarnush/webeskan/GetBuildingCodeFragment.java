package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.models.ResidentPanel.Unit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetBuildingCodeFragment extends DialogFragment {
    View view;
    Button btn_get_units;
    EditText edt_code;
    public static int size_units;
    public static String buildingTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_get_building_code, container, false);
        edt_code = view.findViewById(R.id.edt_code);
        btn_get_units = view.findViewById(R.id.btn_get_units);
        btn_get_units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendJsonArrayRequest_get_unit();

            }
        });

        return view;
    }

    public void sendJsonArrayRequest_get_unit() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://api.webeskan.com/api/v1/users/unit-list/" + edt_code.getText().toString();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                size_units = response.length();
                try {
                    buildingTitle = response.getJSONObject(0).getString("buildingTitle");
                    Toast.makeText(getContext(), buildingTitle, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                GetBuildingCodeFragment.this.dismiss();
                NewunitFragment fragment = new NewunitFragment();
                fragment.show(Resident_panelActivity.fragmentManager, "dialog2");
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }
}