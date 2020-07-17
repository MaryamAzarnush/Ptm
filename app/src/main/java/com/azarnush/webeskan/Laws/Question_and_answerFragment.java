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
import com.azarnush.webeskan.Adapter.Laws.Question_and_answerAdapter;
import com.azarnush.webeskan.HomeActivity;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo4;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class Question_and_answerFragment extends Fragment {
    RecyclerView recycler_question;
    private Question_and_answerAdapter adapter;
    public static ArrayList<LawInfo4> lawinfos4 = new ArrayList<>();

    Context context_Question_and_answer;
    private Realm realm4;
    public static String lawContent4;
    public static String lawTitle4;
    public static String lawTag4;
    public static String lawSummery4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_question_and_answer, container, false);
        context_Question_and_answer = getContext();
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);
        realm4 = Realm.getDefaultInstance();
        Integer chekeRealm = realm4.where(LawInfo4.class).findAll().size();
        All_lawsFragment a = new All_lawsFragment();

        if (chekeRealm == 0) {
            if (a.isConnected()) {
                sendJsonArrayRequest_lawsInfos4();
            } else
                Toast.makeText(getContext(), "لطفا اینترنت را روشن و دوباره امتحان کنید.", Toast.LENGTH_LONG).show();

        } else {
            readFromRealmDatabase();
        }

        recycler_question = root.findViewById(R.id.recycler_question);
        adapter = new Question_and_answerAdapter(lawinfos4, getContext());
        recycler_question.setLayoutManager(new LinearLayoutManager(context_Question_and_answer));
        recycler_question.setAdapter(adapter);

        return root;
    }


    private void writeToRealmDatabase() {
        realm4.beginTransaction();
        for (int i = 0; i < lawinfos4.size(); i++) {

            realm4.copyToRealm(lawinfos4.get(i));
        }

        realm4.commitTransaction();

    }

    private void readFromRealmDatabase() {
        lawinfos4.clear();

        RealmResults<LawInfo4> results = realm4.where(LawInfo4.class).findAll();

        try {
            for (int i = 0; i < results.size(); i++) {

                LawInfo4 object = results.get(i);
                lawTitle4 = object.getLawTitle();
                lawSummery4 = object.getLawSummary();
                String lawId = "5";
                lawContent4 = object.getLawContent();
                String lawSourceLink = "";
                lawTag4 = object.getLawTag();
                String shortKey = "";
                String visibleStatusId = "1";
                String registerDate = "";
                String lawGroupRefId = "1";

                lawinfos4.add(new LawInfo4(lawId, lawTitle4, lawSummery4, lawContent4, lawSourceLink,
                        lawTag4, shortKey, visibleStatusId, registerDate, lawGroupRefId));

            }

            adapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendJsonArrayRequest_lawsInfos4() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/laws/get-laws-by-group-id/1";

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        lawTitle4 = object.getString("lawTitle");
                        String lawSummery = object.getString("lawSummary");
                        String lawId = "5";
                        lawContent4 = object.getString("lawContent");
                        String lawSourceLink = "";
                        lawTag4 = object.getString("lawTag");
                        String shortKey = "";
                        String visibleStatusId = "1";
                        String registerDate = "";
                        String lawGroupRefId = "1";

                        lawinfos4.add(new LawInfo4(lawId, lawTitle4, lawSummery, lawContent4, lawSourceLink,
                                lawTag4, shortKey, visibleStatusId, registerDate, lawGroupRefId));

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
                Toast.makeText(context_Question_and_answer, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);
    }

}
