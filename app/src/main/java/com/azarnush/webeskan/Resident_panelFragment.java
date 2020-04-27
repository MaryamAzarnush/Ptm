package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.models.LawInfo6;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;


public class Resident_panelFragment extends Fragment {
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resident_panelActivity.toolbar.setTitle("واحدهای شما");

        HomeActivity.navigationView.getMenu().findItem(R.id.nav_exit_Account).setVisible(true);

        View root = inflater.inflate(R.layout.fragment_resident_panel, container, false);

        FloatingActionButton fab = root.findViewById(R.id.fab_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        sendJsonArrayRequest_get_units();
        textView = root.findViewById(R.id.textView19);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Resident_panelActivity.toolbar.setTitle("واحدهای شما");
    }

    public void sendJsonArrayRequest_get_units() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://api.webeskan.com/api/v1/users/get-units/" + Login_residentFragment.user_id;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    textView.setText(response.toString());

                } catch (Exception e) {
                    //textView.setText(e.toString());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                textView.setText(error.getClass().getName());
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }
}
