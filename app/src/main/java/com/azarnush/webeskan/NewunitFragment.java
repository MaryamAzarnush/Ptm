package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.azarnush.webeskan.models.ResidentPanel.Unit;

import java.util.ArrayList;
import java.util.List;

public class NewunitFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    View view;
    Spinner spinner3, spinner4;
    List<String> number_of_units = new ArrayList<>();
    List<String> type_of_ownership = new ArrayList<>();
    Button btn_unitRegister;
    String unit_title_select;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newunit, container, false);
        spinner3 = view.findViewById(R.id.spinner3);
        spinner4 = view.findViewById(R.id.spinner4);
        btn_unitRegister = view.findViewById(R.id.btn_unitRegister);
        btn_unitRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewunitFragment.this.dismiss();
                Resident_panelFragment.units.add(new Unit(++Resident_panelFragment.i + "", GetBuildingCodeFragment.buildingTitle, unit_title_select, null));
                Resident_panelFragment.adapter.notifyDataSetChanged();
            }
        });

        for (int i = 1; i < GetBuildingCodeFragment.size_units; i++) {
            number_of_units.add("واحد " + i);
        }

        type_of_ownership.add("فقط ساکن هستم");
        type_of_ownership.add("فقط مالک هستم");
        type_of_ownership.add("ساکن و مالک هستم");

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, type_of_ownership);
        spinner4.setAdapter(dataAdapter4);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, number_of_units);
        spinner3.setAdapter(dataAdapter3);
        spinner3.setOnItemSelectedListener(this);


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        unit_title_select = parent.getItemAtPosition(position).toString();

//        switch ((int) id){
//            case R.id.spinner3:
//                unit_title_select ="vahed 4";
//
//                break;
//        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}