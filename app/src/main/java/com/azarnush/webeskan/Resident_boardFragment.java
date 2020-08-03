package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Adapter.ResidentPanel.BoardInfos_adapter;
import com.azarnush.webeskan.Adapter.ResidentPanel.Debts_adapter;
import com.azarnush.webeskan.Adapter.ResidentPanel.UnitsAdapter;
import com.azarnush.webeskan.models.ResidentPanel.BoardInfo;
import com.azarnush.webeskan.models.ResidentPanel.Debt;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class Resident_boardFragment extends Fragment {
    View root;
    public static JSONObject responsee;
    public static TextView txt_sum;
    TextView building_NameAndUnit;
    TextView total_debt, total_Credit, your_credit;
    RecyclerView debts, the_latest_payments, announcements;
    private Debts_adapter debts_adapter;
    public static List<Debt> debtList = new ArrayList<>();
    public static CheckBox ch_all;
    public static List<BoardInfo> boardList = new ArrayList<>();
    private BoardInfos_adapter boardInfos_adapter;
    Button btn_payment;
    public static String sum;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_resident_board, container, false);
        initViews();
        Resident_panelActivity.toolbar.setTitle("پنل ساکن");
        Toast.makeText(getContext(), UnitsAdapter.residenceRefId.toString(), Toast.LENGTH_LONG).show();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sendJsonObjectRequest_getResidenceDashboard();

                debts_adapter = new Debts_adapter(debtList);
                debts.setLayoutManager(new LinearLayoutManager(getActivity()));
                debts.setAdapter(debts_adapter);

                boardInfos_adapter = new BoardInfos_adapter(boardList);
                announcements.setLayoutManager(new LinearLayoutManager(getActivity()));
                announcements.setAdapter(boardInfos_adapter);


            }
        });
        ch_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    for (int i = 0; i < debtList.size(); i++) {
                        debtList.get(i).setCheckDebt(true);
                        Log.i("ptm", String.valueOf(debtList.get(i).isCheckDebt()));
                    }
                } else {
                    for (int i = 0; i < debtList.size(); i++) {
                        debtList.get(i).setCheckDebt(false);
                        Log.i("ptm", String.valueOf(debtList.get(i).isCheckDebt()));
                    }
                }
                debts_adapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    private void initViews() {
        building_NameAndUnit = root.findViewById(R.id.building_NameAndUnit);
        total_debt = root.findViewById(R.id.total_debt);
        total_Credit = root.findViewById(R.id.total_Credit);
        announcements = root.findViewById(R.id.announcements);
        your_credit = root.findViewById(R.id.your_credit);
        the_latest_payments = root.findViewById(R.id.the_latest_payments);
        debts = root.findViewById(R.id.debts);
        ch_all = root.findViewById(R.id.ch_all);
        txt_sum = root.findViewById(R.id.txt_sum);
        btn_payment = root.findViewById(R.id.btn_payment);
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sum = txt_sum.getText().toString();
                if (sum != "" && Double.valueOf(sum) != 0.0) {
                    Fragment fragment = new PaymentFragment();
                    Resident_panelActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_resident, fragment).addToBackStack(null).commit();
                }

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        ch_all.setChecked(false);
        Debts_adapter.sum_selected_debts = 0.0;
        debtList.clear();
        boardList.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        Resident_panelActivity.toolbar.setTitle("پنل ساکن");
        building_NameAndUnit.setText(UnitsAdapter.building_NameAndUnit);

    }

    public void sendJsonObjectRequest_getResidenceDashboard() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        ////93
        String url = "http://api.webeskan.com/api/v1/residence/dashboard/" + UnitsAdapter.residenceRefId;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                responsee = response;
                parseResponse();
                //Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(8000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    public void parseResponse() {
        try {
            if (responsee.getJSONArray("boardList").length() != 0) {
                boardList.clear();
                JSONArray jsonArray = responsee.getJSONArray("boardList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    Log.i("ptm", jo.toString());
                    BoardInfo boardInfo = new BoardInfo(jo.getInt("boardId"), jo.getString("boardTitle"),
                            jo.getString("boardDescription"), jo.getString("registerDate"),
                            jo.getString("expireDate"), jo.getString("boardPhoto"), jo.getString("senderUserName"),
                            jo.getString("boardGroup"));
                    boardList.add(boardInfo);
                }
                boardInfos_adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }

        try {
            if (responsee.getJSONArray("receiveList").length() != 0) {
                JSONArray jsonArray = responsee.getJSONArray("receiveList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.i("ptm", jsonObject.toString());
                }
            }
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            if (responsee.getJSONArray("debtList").length() != 0) {
                debtList.clear();
                JSONArray jsonArray = responsee.getJSONArray("debtList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jo = jsonArray.getJSONObject(i);
                    // Log.i("ptm", jo.toString());
                    Debt debt = new Debt(jo.getBoolean("checkedDebt"), jo.getInt("actionType"), jo.getInt("debtId"),
                            jo.getDouble("payableDebtAmount"), jo.getDouble("debtAmount"), jo.getString("debtDate"),
                            jo.getString("debtTitle"), jo.getInt("residenceId"));
                    debtList.add(debt);
                }
                debts_adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            if (responsee.getJSONArray("creditList").length() != 0) {
                JSONArray jsonArray = responsee.getJSONArray("creditList");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.i("ptm", jsonObject.toString());
                }
            }
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            Double debtorAmount = responsee.getDouble("debtorAmount");
            total_debt.setText(String.valueOf(debtorAmount));
            Log.i("ptm", String.valueOf(debtorAmount));
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            Double creditorAmount = responsee.getDouble("creditorAmount");
            total_Credit.setText(String.valueOf(creditorAmount));
            Log.i("ptm", String.valueOf(creditorAmount));
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
        try {
            Double cashAmount = responsee.getDouble("cashAmount");
            your_credit.setText(String.valueOf(cashAmount));
            Log.i("ptm", String.valueOf(cashAmount));
        } catch (JSONException e) {
            Log.i("ptm", e.toString());
        }
    }
}