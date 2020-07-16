package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.UnitsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class Resident_boardFragment2 extends Fragment {
    View root;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_resident_board2, container, false);
        // initViews();
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

    @Override
    public void onResume() {
        super.onResume();
        Resident_panelActivity.toolbar.setTitle("پنل ساکن");
    }

    public void sendJsonObjectRequest_getResidenceDashboard() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        ////+ UnitsAdapter.residenceRefId
        String url = "http://api.webeskan.com/api/v1/residence/dashboard/93";

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String s = "";
                    JSONArray jsonArray = response.getJSONArray("debtList");
                    Log.i("ptm", jsonArray.get(0).toString());

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        s = s + (jsonObject.getInt("debtAmount")) + "\n";
                    }
                    // debts.setText(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                Log.i("ptm", response.toString());
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
}
