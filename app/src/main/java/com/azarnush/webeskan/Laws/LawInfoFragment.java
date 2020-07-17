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
import com.azarnush.webeskan.Adapter.Laws.LawInfo_adapter;
import com.azarnush.webeskan.HomeActivity;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LawInfoFragment extends Fragment {


    RecyclerView recyclerViewinfo;
    private LawInfo_adapter adapter;
    private ArrayList<LawInfo> lawInfos = new ArrayList<>();
    Context context_lawinfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_law_info, container, false);

        sendJsonArrayRequest_lawsInfos();

        context_lawinfo = getContext();
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);
        recyclerViewinfo = root.findViewById(R.id.recycler_lawinfo);
        adapter = new LawInfo_adapter(lawInfos, context_lawinfo);

        recyclerViewinfo.setLayoutManager(new LinearLayoutManager(context_lawinfo));
        recyclerViewinfo.setAdapter(adapter);
        return root;
    }


    public void sendJsonArrayRequest_lawsInfos() {
        RequestQueue queue = Volley.newRequestQueue(context_lawinfo);
        String url = "http://api.webeskan.com/api/v1/laws/get-laws-by-group-id/1";

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        String lawTitle = object.getString("lawTitle");
                        String lawSummery = object.getString("lawSummary");
                        String lawId = "5";
                        String lawContent = "";
                        String lawSourceLink = "";
                        String lawTag = "";
                        String shortKey = "";
                        String visibleStatusId = "1";
                        String registerDate = "";
                        String lawGroupRefId = "1";

                        lawInfos.add(new LawInfo(lawId, lawTitle, lawSummery, lawContent, lawSourceLink, lawTag, shortKey, visibleStatusId, registerDate, lawGroupRefId));

                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context_lawinfo, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);

    }

}
