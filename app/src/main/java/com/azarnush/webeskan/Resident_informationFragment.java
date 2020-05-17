package com.azarnush.webeskan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.azarnush.webeskan.Account_register.BooleanRequest;
import com.azarnush.webeskan.ui.home.HomeFragment;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resident_informationFragment extends Fragment implements TextWatcher {

    SharedPreferences shPref;
    JSONObject jsonObject;
    EditText edt_name_Residents;
    EditText edt_family_Residents;
    EditText edt_Password;
    EditText edt_Repeat_Password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_resident_information, container, false);
        HomeActivity.toolbar.setTitle("ورود ساکنین");
        final TextView txt_number_phone = root.findViewById(R.id.txt_number_phone);
        edt_name_Residents = root.findViewById(R.id.edt_name_Residents);
        edt_family_Residents = root.findViewById(R.id.edt_family_Residents);
        edt_Password = root.findViewById(R.id.edt_Password);
        edt_Repeat_Password = root.findViewById(R.id.edt_Repeat_Password);
        final EditText edt_imail = root.findViewById(R.id.edt_imail);
        final Button btn_registration = root.findViewById(R.id.btn_registration);

        edt_name_Residents.addTextChangedListener(this);
        edt_family_Residents.addTextChangedListener(this);
        edt_Password.addTextChangedListener(this);
        edt_Repeat_Password.addTextChangedListener(this);
        txt_number_phone.setText(Get_number_residentFragment.mobile_number);
        shPref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);


        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String mobile = txt_number_phone.getText().toString();
                    String firstName = edt_name_Residents.getText().toString();
                    String lastName = edt_family_Residents.getText().toString();
                    String password = edt_Password.getText().toString();
                    String confirmPassword = edt_Repeat_Password.getText().toString();
                    String email = edt_imail.getText().toString();

                    if (!password.equals(confirmPassword) && !confirmPassword.equals("") && !password.equals("")) {
                        edt_Repeat_Password.setError("تکرار کلمه عبور اشتباه میباشد");
                    }

                    if (firstName.equals("")) {
                        edt_name_Residents.setError("لطفا نام را وارد نمایید");
                    }
                    if (lastName.equals("")) {
                        edt_family_Residents.setError("لطفا نام خانوادگی را وارد نمایید");
                    }
                    if (password.equals("")) {
                        edt_Password.setError("لطفا کلمه عبور را وارد نمایید");
                    }
                    if (confirmPassword.equals("")) {
                        edt_Repeat_Password.setError("لطفا تکرار کلمه عبور را وارد نمایید");
                    }

                    if (!firstName.equals("") && !lastName.equals("") && !password.equals("") && !confirmPassword.equals("")) {
                        if (isValidPassword(password)) {
                            if (password.equals(confirmPassword)) {
                                jsonObject = new JSONObject();
                                jsonObject.put("Mobile", mobile);
                                jsonObject.put("FirstName", firstName);
                                jsonObject.put("LastName", lastName);
                                jsonObject.put("Password", password);
                                jsonObject.put("ConfirmPassword", confirmPassword);
                                jsonObject.put("Email", email);

                                booleanRequest_user_register();

                                SharedPreferences.Editor sEdit = shPref.edit();
                                sEdit.putString("Mobile", mobile);
                                sEdit.putString("FirstName", firstName);
                                sEdit.putString("LastName", lastName);
                                sEdit.putString("Password", password);
                                sEdit.putString("ConfirmPassword", confirmPassword);
                                sEdit.putString("Email", email);
                                sEdit.apply();
                            }
                        }
                    }
                    // Toast.makeText(getContext(), "شما ثبت نام شدید", Toast.LENGTH_LONG).show();
                    //  Toast.makeText(getContext(), registerAccount.toString(), Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        // final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-z])(?=\\S+$).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isValidName(final String name) {

        Pattern pattern;
        Matcher matcher;

        // final String PASSWORD_PATTERN = "^[\u0600-\u06FF]+$";
        final String Name_PATTERN = "[آ-ی ]+";
        pattern = Pattern.compile(Name_PATTERN);
        matcher = pattern.matcher(name);

        return matcher.matches();

    }

    private void booleanRequest_user_register() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        String url = "http://api.webeskan.com/api/v1/users/register";

        try {

            BooleanRequest booleanRequest = new BooleanRequest(Request.Method.POST, url, jsonObject.toString(), new Response.Listener<Boolean>() {
                @Override
                public void onResponse(Boolean response) {
                    Toast.makeText(getContext(), String.valueOf(response), Toast.LENGTH_SHORT).show();
                    if (response == true) {
                        // HomeActivity.fragmentManager.popBackStack();


                        SharedPreferences.Editor sEdit = shPref.edit();
                        sEdit.putBoolean("is register", true);
                        sEdit.apply();
                        SharedPreferences.Editor sEdit2 = HomeFragment.homePref.edit();
                        sEdit2.putBoolean("is login", true);
                        sEdit2.apply();
                        Toast.makeText(getContext(), "ثبت نام انجام شد", Toast.LENGTH_SHORT).show();


                        sendJSONObjectRequest_login();

                        Fragment fragment = new Get_number_residentFragment();


                        HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

//                        HomeActivity.fragmentManager.popBackStack();
//                        getActivity().finish();
//                        startActivity(new Intent(getContext(), Resident_panelActivity.class));


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            // Add the request to the RequestQueue.
            queue.add(booleanRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendJSONObjectRequest_login() {
        String mobile_number = shPref.getString("Mobile", "");
        String codRegister = shPref.getString("codRegister", "");
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://api.webeskan.com/api/v1/users/" + "Login/" + mobile_number + "/" + codRegister;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String user_id = response.getString("reasonPhrase");

                    shPref.edit().putString("reasonPhrase", user_id).apply();
                    Toast.makeText(getContext(), user_id, Toast.LENGTH_LONG).show();


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
        queue.add(request);

    }


    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("ورود ساکنین");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        String name = edt_name_Residents.getText().toString();

        if (!isValidName(name)) {
            edt_name_Residents.setError("فقط حروف فارسی قابل قبول هست");

        }

        String family = edt_family_Residents.getText().toString();
        if (!isValidName(family)) {
            edt_family_Residents.setError("فقط حروف فارسی قابل قبول هست");
        }

        String password = edt_Password.getText().toString();
        if (isValidPassword(password)) {
            edt_Password.setTextColor(Color.parseColor("#00574B"));
        } else {

            edt_Password.setTextColor(Color.parseColor("#D81B60"));
            edt_Password.setError("رمز ترکیبی از حداقل 6 حروف انگلیسی بزرگ و کوچک و اعداد میباشد");

            //Toast.makeText(getContext(), "رمز ترکیبی از 8 حروف انگلیسی کوچک و بزرگ و اعداد میباشد", Toast.LENGTH_LONG).show();
        }


        String confirmPassword = edt_Repeat_Password.getText().toString();
        if (confirmPassword.equals(password)) {
            edt_Repeat_Password.setTextColor(Color.parseColor("#00574B"));
        } else
            edt_Repeat_Password.setTextColor(Color.parseColor("#D81B60"));
        // edt_Repeat_Password.setError("تکرار کلمه عبور اشتباه میباشد");

    }
}