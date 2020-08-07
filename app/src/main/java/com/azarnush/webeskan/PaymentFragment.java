package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azarnush.webeskan.models.Laws.LawInfo2;
import com.azarnush.webeskan.models.ResidentPanel.Debt;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmResults;

public class PaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    Spinner spinner1, spinner2;
    List<String> payment_type = new ArrayList<>();
    List<String> shaba_number = new ArrayList<>();
    TextView txt_sum_pay, txt_show_debts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        txt_sum_pay = view.findViewById(R.id.txt_sum_pay);
        txt_show_debts = view.findViewById(R.id.txt_show_debts);
        txt_sum_pay.setText(Resident_boardFragment.sum + " تومان");

        RealmResults<Debt> results = Resident_boardFragment.realm.where(Debt.class).findAll();
        String show_debts = "";
        for (int i = 0; i < results.size(); i++) {
            Debt debt = results.get(i);
            Boolean isCheckDebt = debt.isCheckDebt();
            if (isCheckDebt) {
                show_debts = show_debts + debt.debtTitle + " , ";
            }
        }
        txt_show_debts.setText(show_debts);

        Resident_boardFragment.realm.beginTransaction();
        results.deleteAllFromRealm();
        Resident_boardFragment.realm.commitTransaction();

        payment_type.add("پرداخت آنلاین");
        shaba_number.add("get Shaba number");
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);

        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, payment_type);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, shaba_number);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}