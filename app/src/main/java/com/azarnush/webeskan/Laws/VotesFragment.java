package com.azarnush.webeskan.Laws;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.Laws.Votes_adapter;
import com.azarnush.webeskan.HomeActivity;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo5;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class VotesFragment extends Fragment {
    RecyclerView Recycler_votes;
    private Votes_adapter adapter;
    public static ArrayList<LawInfo5> lawinfos5 = new ArrayList<>();

    private Realm realm5;
    public static String lawContent5;
    public static String lawTitle5;
    public static String lawTag5;
    public static String lawSummery5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_votes, container, false);


        realm5 = Realm.getDefaultInstance();
        Integer chekeRealm = realm5.where(LawInfo5.class).findAll().size();
        All_lawsFragment a = new All_lawsFragment();
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);

        if (chekeRealm == 0) {
            if (a.isConnected()) {
                sendJsonArrayRequest_lawsInfos5();
            } else
                Toast.makeText(getContext(), "لطفا اینترنت را روشن و دوباره امتحان کنید.", Toast.LENGTH_LONG).show();

        } else {
            readFromRealmDatabase();

        }

        Recycler_votes = root.findViewById(R.id.recycler_votes);
        adapter = new Votes_adapter(lawinfos5, getContext());
        Recycler_votes.setLayoutManager(new LinearLayoutManager(getContext()));
        Recycler_votes.setAdapter(adapter);


        return root;
    }


    private void writeToRealmDatabase() {
        realm5.beginTransaction();
        for (int i = 0; i < lawinfos5.size(); i++) {

            realm5.copyToRealm(lawinfos5.get(i));
        }
        realm5.commitTransaction();

    }

    private void readFromRealmDatabase() {
        lawinfos5.clear();
        RealmResults<LawInfo5> results = realm5.where(LawInfo5.class).findAll();

        try {
            for (int i = 0; i < results.size(); i++) {

                LawInfo5 object = results.get(i);
                lawTitle5 = object.getLawTitle();
                lawSummery5 = object.getLawSummary();
                String lawId = "5";
                lawContent5 = object.getLawContent();
                String lawSourceLink = "";
                lawTag5 = object.getLawTag();
                String shortKey = "";
                String visibleStatusId = "1";
                String registerDate = "";
                String lawGroupRefId = "1";
                lawinfos5.add(new LawInfo5(lawId, lawTitle5, lawSummery5, lawContent5, lawSourceLink,
                        lawTag5, shortKey, visibleStatusId, registerDate, lawGroupRefId));

            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendJsonArrayRequest_lawsInfos5() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/laws/get-laws-by-group-id/4";

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        lawTitle5 = object.getString("lawTitle");
                        String lawSummery = object.getString("lawSummary");
                        String lawId = "5";
                        lawContent5 = object.getString("lawContent");
                        String lawSourceLink = "";
                        lawTag5 = object.getString("lawTag");
                        String shortKey = "";
                        String visibleStatusId = "1";
                        String registerDate = "";
                        String lawGroupRefId = "1";

                        lawinfos5.add(new LawInfo5(lawId, lawTitle5, lawSummery, lawContent5,
                                lawSourceLink, lawTag5, shortKey, visibleStatusId, registerDate, lawGroupRefId));

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
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }
}
