package com.azarnush.webeskan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.ui.home.HomeFragment;
import com.hendraanggrian.appcompat.widget.PinGroup;

import org.json.JSONObject;


public class Login_residentFragment extends Fragment {
    View root;
    PinGroup pinGroup;
    Button btn_login_with_number, btn_resend;
    TextView txt_Counter;
    public int counter = 30;
    String codRegister;
    Context context;
    public static String isRegister;
    public static String user_id;
    SharedPreferences shPref;

    String url_Foundation = "http://api.webeskan.com/api/v1/users/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_login_resident, container, false);
        HomeActivity.toolbar.setTitle("ورود ساکنین");
        context = getContext();
        pinGroup = root.findViewById(R.id.pinGroup);
        btn_login_with_number = root.findViewById(R.id.login);
        btn_resend = root.findViewById(R.id.btn_resend);
        txt_Counter = root.findViewById(R.id.txt_Counter);
        shPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        sendJSONObjectRequest1();

        count();

        btn_login_with_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user_cod = (String) pinGroup.getText();

                if (codRegister.equalsIgnoreCase(user_cod)) {
                    if (counter >= 0) {
                        if (isRegister == "true") {
                            HomeActivity.fragmentManager.popBackStack();
                            getActivity().finish();
                            startActivity(new Intent(getContext(), Resident_panelActivity.class));
                            sendJSONObjectRequest_login();

                            SharedPreferences.Editor sEdit2 = HomeFragment.homePref.edit();
                            sEdit2.putBoolean("is login", true);
                            sEdit2.putBoolean("is register", true);
                            sEdit2.apply();

                        } else {
                            Fragment fragment = new Resident_informationFragment();
                            HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                                    .addToBackStack(null).commit();
                        }

                    } else
                        Toast.makeText(getContext(), "زمان شما تمام شد", Toast.LENGTH_LONG).show();

                } else
                    Toast.makeText(getContext(), "کد ارسالی شما اشتباه می باشد", Toast.LENGTH_LONG).show();
            }
        });
        btn_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter == 0) {

                    sendJSONObjectRequest2();
                    btn_resend.setBackgroundColor(Color.parseColor("#575757"));
                    btn_resend.setEnabled(false);

                    counter = 30;
                }
            }
        });
        return root;
    }

    //Seconds build
    public void count() {
        new CountDownTimer(100000000, 1000) {
            public void onTick(long millisUntilFinished) {
                txt_Counter.setText(String.valueOf(counter));
                if (counter != 0) {
                    counter--;
                }
                if (counter <=0){

                    btn_resend.setBackgroundColor(Color.parseColor("#2D9385"));
                    btn_resend.setEnabled(true);


                }
            }
            public void onFinish() {

                counter = 30;
            }
        }.start();
    }

    public void sendJSONObjectRequest1() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = url_Foundation + "generate-user-code/" + Get_number_residentFragment.mobile_number;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    isRegister = response.getString("item1");
                    codRegister = response.getString("item2");
                    Toast.makeText(getContext(), isRegister, Toast.LENGTH_LONG).show();
                    pinGroup.setText(codRegister);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    public void sendJSONObjectRequest2() {
        pinGroup.setText("");

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = url_Foundation + "generate-user-code/" + Get_number_residentFragment.mobile_number;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    //isRegister = response.getString("item1");
                    codRegister = response.getString("item2");
                    Toast.makeText(context, codRegister, Toast.LENGTH_LONG).show();
                    pinGroup.setText(codRegister);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);

    }

    public void sendJSONObjectRequest_login() {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = url_Foundation + "Login/" + Get_number_residentFragment.mobile_number + "/" + codRegister;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    user_id = response.getString("reasonPhrase");
                    SharedPreferences.Editor sEdit = shPref.edit();
                    sEdit.putString("reasonPhrase", user_id);
                    sEdit.apply();
                    Toast.makeText(context, user_id, Toast.LENGTH_LONG).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, listener, errorListener);
        queue.add(request);

    }


    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("ورود ساکنین");
    }
}
