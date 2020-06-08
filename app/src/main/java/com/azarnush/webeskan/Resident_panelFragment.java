package com.azarnush.webeskan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.UnitsAdapter;
import com.azarnush.webeskan.models.Unit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Resident_panelFragment extends Fragment {
    TextView textView;
    public static List<Unit> units = new ArrayList<>();
    private RecyclerView recyclerView;
    private UnitsAdapter adapter;
    SharedPreferences shPref;
    String user_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resident_panelActivity.toolbar.setTitle("واحدهای شما");

        HomeActivity.navigationView.getMenu().findItem(R.id.nav_exit_Account).setVisible(true);

        View root = inflater.inflate(R.layout.fragment_resident_panel, container, false);

        shPref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        FloatingActionButton fab = root.findViewById(R.id.fab_new);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Fragment fragment = new Resident_boardFragment();
                Resident_panelActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_resident, fragment).addToBackStack(null).commit();
            }
        });
        textView = root.findViewById(R.id.textView19);


        recyclerView = root.findViewById(R.id.recycler_units);


        adapter = new UnitsAdapter(units);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        sendJsonArrayRequest_get_units();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Resident_panelActivity.toolbar.setTitle("واحدهای شما");

    }

    @Override
    public void onStart() {
        super.onStart();
        sendJsonArrayRequest_get_units();
    }

    public void sendJsonArrayRequest_get_units() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        user_id = shPref.getString("reasonPhrase", "");
        String url = "http://api.webeskan.com/api/v1/users/get-units/" + user_id;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                // Toast.makeText(getContext(), user_id, Toast.LENGTH_LONG).show();

                try {
                    if (response == null || response.toString() == "") {
                        textView.setText("null");
                    }

                    if (response.length() == 0) {
                        textView.setText("شما واحد ثبت شده ندارید لطفا ثبت نمایید");
                    }
                    if (response.toString().equalsIgnoreCase("کاربری با این مشخصات یافت نشد")) {
                        textView.setText("کاربری با این مشخصات یافت نشد");
                    }
                    units.clear();

                    for (int i = 0; i < response.length(); i++) {

                        JSONObject object = response.getJSONObject(i);


                        String buildingTitle = object.getString("buildingTitle");
                        String unitTitle = object.getString("unitTitle");

                        units.add(new Unit(String.valueOf(i + 1), buildingTitle, unitTitle));
                        Toast.makeText(getContext(), units.size() + "", Toast.LENGTH_LONG).show();
                    }

                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    textView.setText(e.toString());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                textView.setText(error.getMessage());
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(500, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }
}
