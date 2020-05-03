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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.ClientError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.UnitsAdapter;
import com.azarnush.webeskan.models.LawInfo6;
import com.azarnush.webeskan.models.Unit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.azarnush.webeskan.Login_residentFragment.shPref;
import static com.azarnush.webeskan.Login_residentFragment.user_id;

public class Resident_panelFragment extends Fragment {
    TextView textView;
    private List<Unit> units = new ArrayList<>();
    private RecyclerView recyclerView;
    private UnitsAdapter adapter;
    SharedPreferences shPref ;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        textView = root.findViewById(R.id.textView19);



        recyclerView = root.findViewById(R.id.recycler_units);

        adapter = new UnitsAdapter(units);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        sendJsonArrayRequest_get_units();
        adapter.notifyDataSetChanged();
       // setData();



        return root;
    }
//    private void setData(){
//        units.add(new Unit("2","بیتا", "واحد 3"));
//        adapter.notifyDataSetChanged();
//    }

    @Override
    public void onResume() {
        super.onResume();
        Resident_panelActivity.toolbar.setTitle("واحدهای شما");
    }

    public void sendJsonArrayRequest_get_units() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
       user_id = shPref.getString("reasonPhrase", null);
        String url = "http://api.webeskan.com/api/v1/users/get-units/" + user_id;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for(int i =0; i<response.length(); i++){
                        JSONObject object = response.getJSONObject(i);
                       // textView.setText(object.getString("buildingTitle"));
                       // units.add(new Unit(String.valueOf(++i) , object.getString("buildingTitle") ,object.getString("unitTitle")));
                        String buildingTitle =  object.getString("buildingTitle");
                        String unitTitle = object.getString("unitTitle");
                        units.add(new Unit(String.valueOf(++i), buildingTitle,unitTitle));

                    }

                    //textView.setText(response.toString());

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
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }
}
