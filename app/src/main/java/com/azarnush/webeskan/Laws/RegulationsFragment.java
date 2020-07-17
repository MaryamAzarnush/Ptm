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
import com.azarnush.webeskan.Adapter.Laws.Regulations_adapter;
import com.azarnush.webeskan.HomeActivity;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo3;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RegulationsFragment extends Fragment {
    RecyclerView recycler_regulations;
    private Regulations_adapter adapter;
    public static ArrayList<LawInfo3> lawinfos3 = new ArrayList<>();
    Context context_regulations;
    private Realm realm;
    public static String lawContent3;
    public static String lawTitle3;
    public static String lawTag3;
    public static String lawSummery3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_regulations, container, false);

        context_regulations = getContext();

        realm = Realm.getDefaultInstance();
        Integer chekeRealm = realm.where(LawInfo3.class).findAll().size();
        All_lawsFragment a = new All_lawsFragment();
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);
        if (chekeRealm == 0) {
            if (a.isConnected()) {
                sendJsonArrayRequest_lawsInfos3();
            } else
                Toast.makeText(context_regulations, "لطفا اینترنت را روشن و دوباره امتحان کنید.", Toast.LENGTH_LONG).show();

        } else {
            readFromRealmDatabase();
        }

        recycler_regulations = root.findViewById(R.id.recycler_regulations);
        adapter = new Regulations_adapter(lawinfos3, context_regulations);
        recycler_regulations.setLayoutManager(new LinearLayoutManager(context_regulations));
        recycler_regulations.setAdapter(adapter);
        return root;
    }


    private void writeToRealmDatabase() {
        realm.beginTransaction();
        for (int i = 0; i < lawinfos3.size(); i++) {

            realm.copyToRealm(lawinfos3.get(i));
        }
        realm.commitTransaction();

    }

    private void readFromRealmDatabase() {
        lawinfos3.clear();
        RealmResults<LawInfo3> results = realm.where(LawInfo3.class).findAll();

        try {
            for (int i = 0; i < results.size(); i++) {

                LawInfo3 object = results.get(i);
                lawTitle3 = object.getLawTitle();
                lawSummery3 = object.getLawSummary();
                String lawId = "5";
                lawContent3 = object.getLawContent();
                String lawSourceLink = "";
                lawTag3 = object.getLawTag();
                String shortKey = "";
                String visibleStatusId = "1";
                String registerDate = "";
                String lawGroupRefId = "1";
                lawinfos3.add(new LawInfo3(lawId, lawTitle3, lawSummery3, lawContent3, lawSourceLink, lawTag3, shortKey, visibleStatusId, registerDate, lawGroupRefId));

            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendJsonArrayRequest_lawsInfos3() {
        RequestQueue queue = Volley.newRequestQueue(context_regulations);
        String url = "http://api.webeskan.com/api/v1/laws/get-laws-by-group-id/3";

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        lawTitle3 = object.getString("lawTitle");
                        String lawSummery = object.getString("lawSummary");
                        String lawId = "5";
                        lawContent3 = object.getString("lawContent");
                        String lawSourceLink = "";
                        lawTag3 = object.getString("lawTag");
                        String shortKey = "";
                        String visibleStatusId = "1";
                        String registerDate = "";
                        String lawGroupRefId = "1";

                        lawinfos3.add(new LawInfo3(lawId, lawTitle3, lawSummery, lawContent3, lawSourceLink, lawTag3, shortKey, visibleStatusId, registerDate, lawGroupRefId));


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
                Toast.makeText(context_regulations, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }
}
