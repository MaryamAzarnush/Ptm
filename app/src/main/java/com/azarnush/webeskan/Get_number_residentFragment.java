package com.azarnush.webeskan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Get_number_residentFragment extends Fragment implements View.OnClickListener {
    View root;
    EditText phone;
    Button btn_login, btn_register;
    Integer number_number = 11;
    public Context context;
    public static String mobile_number;
    SharedPreferences shPref;
    public static String isRegister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_get_number_resident, container, false);

        context = getContext();
        phone = root.findViewById(R.id.phone);
        btn_login = root.findViewById(R.id.btn_login);
        btn_register = root.findViewById(R.id.btn_register);
        HomeActivity.toolbar.setTitle("ورود ساکنین");
        shPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        return root;
    }

    public Boolean validation_number() {
        mobile_number = phone.getText().toString();

        if (mobile_number.equals("")) {

            Toast.makeText(getContext(), "لطفا شماره را وارد نمایید", Toast.LENGTH_LONG).show();
            return false;

        } else if (mobile_number.length() < number_number) {
            Toast.makeText(getContext(), "تعداد ارقام کافی نیست", Toast.LENGTH_LONG).show();
            return false;
        } else if (!mobile_number.matches("(\\+98|0)?9\\d{9}")) {

            Toast.makeText(getContext(), "شماره موبایل نامعتبر هست", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;


    }

    public void sendJSONObjectRequest_isRegister1() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/users/" + "generate-user-code/" + mobile_number;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    isRegister = response.getString("item1");
                    switch (isRegister) {
                        case "null":
                            sendJSONObjectRequest_isRegister1();
                        case "true":
                            shPref.edit().putString("Mobile", mobile_number).apply();

                            Fragment fragment = new Login_residentFragment();

                            HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                            break;
                        case "false":
                            Toast.makeText(getContext(), "لطفا ابتدا ثبت نام کنید", Toast.LENGTH_LONG).show();
                            Fragment fragment2 = new Resident_informationFragment();
                            HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment2).commit();
                            break;

                    }

                    shPref.edit().putString("isRegister", isRegister).apply();
                    // Toast.makeText(getContext(), isRegister, Toast.LENGTH_LONG).show();


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

    public void sendJSONObjectRequest_isRegister2() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/users/" + "generate-user-code/" + mobile_number;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    isRegister = response.getString("item1");
                    switch (isRegister) {
                        case "null":
                            sendJSONObjectRequest_isRegister2();
                        case "true":
                            Toast.makeText(getContext(), "شما با این شماره ثبت نام هستید", Toast.LENGTH_LONG).show();
                            shPref.edit().putString("Mobile", mobile_number).apply();

                            Fragment fragment = new Login_residentFragment();

                            HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                            break;
                        case "false":

                            Fragment fragment2 = new Resident_informationFragment();
                            HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment2).commit();
                            break;

                    }

                    shPref.edit().putString("isRegister", isRegister).apply();
                    // Toast.makeText(getContext(), isRegister, Toast.LENGTH_LONG).show();


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


    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("ورود ساکنین");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:

                if (validation_number()) {
                    sendJSONObjectRequest_isRegister1();
                }

                break;

            case R.id.btn_register:

                if (validation_number()) {
                    sendJSONObjectRequest_isRegister2();
                }
                break;
        }
    }
}

