package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.UnitsAdapter;
import com.azarnush.webeskan.models.Unit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Resident_boardFragment2 extends Fragment {
    View root;
    public static JSONObject responsee;
    TextView building_NameAndUnit;
    TextView total_debt, total_Credit, your_credit;
    RecyclerView debts, the_latest_payments, announcements;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_resident_board2, container, false);
        initViews();
        Resident_panelActivity.toolbar.setTitle("پنل ساکن");
        Toast.makeText(getContext(), UnitsAdapter.residenceRefId.toString(), Toast.LENGTH_LONG).show();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sendJsonObjectRequest_getResidenceDashboard();
            }
        });
        return root;
    }

    private void initViews() {
        building_NameAndUnit = root.findViewById(R.id.building_NameAndUnit);
        total_debt = root.findViewById(R.id.total_debt);
        total_Credit = root.findViewById(R.id.total_Credit);
        announcements = root.findViewById(R.id.announcements);
        your_credit = root.findViewById(R.id.your_credit);
        the_latest_payments = root.findViewById(R.id.the_latest_payments);
        debts = root.findViewById(R.id.debts);

    }

    @Override
    public void onResume() {
        super.onResume();
        Resident_panelActivity.toolbar.setTitle("پنل ساکن");
        building_NameAndUnit.setText(UnitsAdapter.building_NameAndUnit);
    }

    public void sendJsonObjectRequest_getResidenceDashboard() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        ////+ UnitsAdapter.residenceRefId
        String url = "http://api.webeskan.com/api/v1/residence/dashboard/93";

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responsee = response;
                parseResponse();

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(8000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }

    public void parseResponse() {
        try {
            if (responsee.getJSONArray("boardList").length() != 0) {
                JSONArray jsonArray = responsee.getJSONArray("boardList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.i("ptm", jsonObject.toString());
                }
            }
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }

        try {
            if (responsee.getJSONArray("receiveList").length() != 0) {
                JSONArray jsonArray = responsee.getJSONArray("receiveList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.i("ptm", jsonObject.toString());
                }
            }
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            if (responsee.getJSONArray("debtList").length() != 0) {
                JSONArray jsonArray = responsee.getJSONArray("debtList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.i("ptm", jsonObject.toString());
                }
            }
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            if (responsee.getJSONArray("creditList").length() != 0) {
                JSONArray jsonArray = responsee.getJSONArray("creditList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.i("ptm", jsonObject.toString());
                }
            }
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            Double debtorAmount = responsee.getDouble("debtorAmount");
            total_debt.setText(String.valueOf(debtorAmount));
            Log.i("ptm", String.valueOf(debtorAmount));
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            Double creditorAmount = responsee.getDouble("creditorAmount");
            total_Credit.setText(String.valueOf(creditorAmount));
            Log.i("ptm", String.valueOf(creditorAmount));
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            Double cashAmount = responsee.getDouble("cashAmount");
            your_credit.setText(String.valueOf(cashAmount));
            Log.i("ptm", String.valueOf(cashAmount));
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
    }
}
