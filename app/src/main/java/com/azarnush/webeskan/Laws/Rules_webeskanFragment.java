package com.azarnush.webeskan.Laws;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.Laws.Rules_webeskan_adapter;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo6;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class Rules_webeskanFragment extends Fragment {
    RecyclerView recycler_Rules_webeskan;
    private Rules_webeskan_adapter adapter;
    public static ArrayList<LawInfo6> lawinfos6 = new ArrayList<>();
    Context context_Rules_webeskan;
    private Realm realm6;
    public static String lawContent6;
    public static String lawTitle6;
    public static String lawTag6;
    public static String lawSummery6;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_rules_webeskan, container, false);

        context_Rules_webeskan = getContext();

        realm6 = Realm.getDefaultInstance();
        Integer chekeRealm = realm6.where(LawInfo6.class).findAll().size();
        All_lawsFragment a = new All_lawsFragment();

        if (chekeRealm == 0) {
            if (a.isConnected()) {
                sendJsonArrayRequest_lawsInfos6();
            } else
                Toast.makeText(getContext(), "لطفا اینترنت را روشن و دوباره امتحان کنید.", Toast.LENGTH_LONG).show();

        } else {
            readFromRealmDatabase();
        }

        recycler_Rules_webeskan = root.findViewById(R.id.recycler_Rules_webeskan);
        adapter = new Rules_webeskan_adapter(lawinfos6, context_Rules_webeskan);
        recycler_Rules_webeskan.setLayoutManager(new LinearLayoutManager(context_Rules_webeskan));
        recycler_Rules_webeskan.setAdapter(adapter);
        return root;
    }


    private void writeToRealmDatabase() {
        realm6.beginTransaction();
        for (int i = 0; i < lawinfos6.size(); i++) {

            realm6.copyToRealm(lawinfos6.get(i));
        }
        realm6.commitTransaction();

    }

    private void readFromRealmDatabase() {
        lawinfos6.clear();
        RealmResults<LawInfo6> results = realm6.where(LawInfo6.class).findAll();

        try {
            for (int i = 0; i < results.size(); i++) {

                LawInfo6 object = results.get(i);
                lawTitle6 = object.getLawTitle();
                lawSummery6 = object.getLawSummary();
                String lawId = "5";
                lawContent6 = object.getLawContent();
                String lawSourceLink = "";
                lawTag6 = object.getLawTag();
                String shortKey = "";
                String visibleStatusId = "1";
                String registerDate = "";
                String lawGroupRefId = "1";
                lawinfos6.add(new LawInfo6(lawId, lawTitle6, lawSummery6, lawContent6, lawSourceLink,
                        lawTag6, shortKey, visibleStatusId, registerDate, lawGroupRefId));

            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendJsonArrayRequest_lawsInfos6() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/laws/get-laws-by-group-id/6";

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        lawTitle6 = object.getString("lawTitle");
                        String lawSummery = object.getString("lawSummary");
                        String lawId = "5";
                        lawContent6 = object.getString("lawContent");
                        String lawSourceLink = "";
                        lawTag6 = object.getString("lawTag");
                        String shortKey = "";
                        String visibleStatusId = "1";
                        String registerDate = "";
                        String lawGroupRefId = "1";

                        lawinfos6.add(new LawInfo6(lawId, lawTitle6, lawSummery, lawContent6,
                                lawSourceLink, lawTag6, shortKey, visibleStatusId, registerDate, lawGroupRefId));

                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                writeToRealmDatabase();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context_Rules_webeskan, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }
}
