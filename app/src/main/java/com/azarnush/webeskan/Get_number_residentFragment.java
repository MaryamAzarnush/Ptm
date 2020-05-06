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


public class Get_number_residentFragment extends Fragment {
    View root;
    EditText phone;
    Button btn_login, btn_register;
    Integer number_number = 11;
    public Context context;
    public static String mobile_number;
    SharedPreferences shPref;


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
        phone.setText(shPref.getString("Mobile", ""));

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get mobile number from view

                mobile_number = phone.getText().toString();


                if (mobile_number.equals("")) {

                    Toast.makeText(getContext(), "لطفا شماره را وارد نمایید", Toast.LENGTH_LONG).show();

                } else if (mobile_number.length() < number_number) {
                    Toast.makeText(getContext(), "تعداد ارقام کافی نیست", Toast.LENGTH_LONG).show();
                } else if (!mobile_number.matches("(\\+98|0)?9\\d{9}")) {

                    Toast.makeText(getContext(), "شماره موبایل نامعتبر هست", Toast.LENGTH_SHORT).show();
                } else {

                    shPref.edit().putString("Mobile", mobile_number).apply();

                    Fragment fragment = new Login_residentFragment();

                    HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get mobile number from view
                mobile_number = phone.getText().toString();

                if (mobile_number.equals("")) {

                    Toast.makeText(getContext(), "لطفا شماره را وارد نمایید", Toast.LENGTH_LONG).show();

                } else if (mobile_number.length() < number_number) {
                    Toast.makeText(getContext(), "تعداد ارقام کافی نیست", Toast.LENGTH_LONG).show();
                } else if (!mobile_number.matches("(\\+98|0)?9\\d{9}")) {

                    Toast.makeText(getContext(), "شماره موبایل نامعتبر هست", Toast.LENGTH_SHORT).show();
                } else {

                    shPref.edit().putString("Mobile", mobile_number).apply();

                    Fragment fragment = new Resident_informationFragment();

                    HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();

                }
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("ورود ساکنین");
    }
}
