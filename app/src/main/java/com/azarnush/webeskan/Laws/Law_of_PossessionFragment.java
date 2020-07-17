package com.azarnush.webeskan.Laws;

import android.content.Context;
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
import com.azarnush.webeskan.Adapter.Laws.LawInfo2_adapter;
import com.azarnush.webeskan.HomeActivity;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class Law_of_PossessionFragment extends Fragment {
    RecyclerView recyclerView;
    private LawInfo2_adapter adapter;
    public static ArrayList<LawInfo2> lawInfos2 = new ArrayList<>();

    public static String lawContent;
    public static String lawTitle;
    public static String lawTag;
    public static String lawSummery;
    Realm realm;
    View root;
    Context context = getActivity();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_law_of__possession, container, false);
        realm = Realm.getDefaultInstance();
        Integer chekeRealm = realm.where(LawInfo2.class).findAll().size();
        All_lawsFragment a = new All_lawsFragment();
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);


        if (chekeRealm == 0) {
            if (a.isConnected()) {
                sendJsonArrayRequest_lawsInfos2();
            } else
                Toast.makeText(context, "لطفا اینترنت را روشن و دوباره امتحان کنید.", Toast.LENGTH_LONG).show();

        } else {
            readFromRealmDatabase();
        }

        recyclerView = root.findViewById(R.id.recycler_law_of_possession);
        adapter = new LawInfo2_adapter(lawInfos2, context);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
        return root;
    }


    private void writeToRealmDatabase() {
        realm.beginTransaction();
        for (int i = 0; i < lawInfos2.size(); i++) {

            realm.copyToRealm(lawInfos2.get(i));
        }

        realm.commitTransaction();

    }

    private void readFromRealmDatabase() {
        lawInfos2.clear();
        RealmResults<LawInfo2> results = realm.where(LawInfo2.class).findAll();

        try {
            for (int i = 0; i < results.size(); i++) {

                LawInfo2 object = results.get(i);
                lawTitle = object.getLawTitle();
                lawSummery = object.getLawSummary();
                String lawId = "5";
                lawContent = object.getLawContent();
                String lawSourceLink = "";
                lawTag = object.getLawTag();
                String shortKey = "";
                String visibleStatusId = "1";
                String registerDate = "";
                String lawGroupRefId = "1";
                lawInfos2.add(new LawInfo2(lawId, lawTitle, lawSummery, lawContent, lawSourceLink, lawTag, shortKey, visibleStatusId, registerDate, lawGroupRefId));

            }
            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendJsonArrayRequest_lawsInfos2() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://api.webeskan.com/api/v1/laws/get-laws-by-group-id/2";

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        lawTitle = object.getString("lawTitle");
                        String lawSummery = object.getString("lawSummary");
                        String lawId = "5";
                        lawContent = object.getString("lawContent");
                        String lawSourceLink = "";
                        lawTag = object.getString("lawTag");
                        String shortKey = "";
                        String visibleStatusId = "1";
                        String registerDate = "";
                        String lawGroupRefId = "1";

                        lawInfos2.add(new LawInfo2(lawId, lawTitle, lawSummery, lawContent,
                                lawSourceLink, lawTag, shortKey, visibleStatusId, registerDate, lawGroupRefId));
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

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);

    }

}

