package com.azarnush.webeskan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.ResidentPanel.UnitsAdapter;
import com.azarnush.webeskan.models.ResidentPanel.Unit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    public static String user_id;
    FloatingActionButton fab_new;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Resident_panelActivity.toolbar.setTitle("واحدهای شما");

        HomeActivity.navigationView.getMenu().findItem(R.id.nav_exit_Account).setVisible(true);

        View root = inflater.inflate(R.layout.fragment_resident_panel, container, false);

        shPref = getContext().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        fab_new = root.findViewById(R.id.fab_new);
        fab_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetBuildingCodeFragment fragment = new GetBuildingCodeFragment();
                // Resident_panelActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_resident, fragment).commit();
                fragment.show(Resident_panelActivity.fragmentManager, "dialog1");
            }
        });


        recyclerView = root.findViewById(R.id.recycler_units);

//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//                sendJsonArrayRequest_get_units();
//            }
//        });

        adapter = new UnitsAdapter(units);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bottom);
        recyclerView.setAnimation(animation);
        recyclerView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Resident_panelActivity.toolbar.setTitle("واحدهای شما");
        sendJsonArrayRequest_get_units();
    }


    public void sendJsonArrayRequest_get_units() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        user_id = shPref.getString("reasonPhrase", "");
        String url = "http://api.webeskan.com/api/v1/users/get-units/" + user_id;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {

                    if (response.length() == 0) {
                        units.clear();
                        adapter.notifyDataSetChanged();
                        textView.setText("شما واحد ثبت شده ندارید لطفا ثبت نمایید");

                        return;
                    }
                    if (response.toString().equalsIgnoreCase("کاربری با این مشخصات یافت نشد")) {
                        textView.setText("کاربری با این مشخصات یافت نشد");
                    }
                    units.clear();

                    for (int i = 0; i < response.length(); i++) {

                        try {
                            JSONObject object = response.getJSONObject(i);
                            String buildingTitle = object.getString("buildingTitle");
                            String unitTitle = object.getString("unitTitle");
                            Integer residenceRefId = object.getInt("residenceRefId");
                            units.add(new Unit(String.valueOf(i + 1), buildingTitle, unitTitle, residenceRefId));
                            // Toast.makeText(getContext(), units.size() + "", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }

                    }

                    adapter.notifyDataSetChanged();
                    textView.setText("");

                } catch (Exception e) {
                    //textView.setText(e.toString());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //textView.setText(error.getMessage());
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);

    }
}