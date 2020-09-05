package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class NewunitFragment extends DialogFragment {
    View view;
    Spinner spinner3, spinner4;
    List<String> number_of_units = new ArrayList<>();
    List<String> type_of_ownership = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_newunit, container, false);
        spinner3 = view.findViewById(R.id.spinner3);
        spinner4 = view.findViewById(R.id.spinner4);

        for (int i = 1; i < GetBuildingCodeFragment.size_units; i++) {
            number_of_units.add(String.valueOf(i));
        }

        type_of_ownership.add("فقط ساکن هستم");
        type_of_ownership.add("فقط مالک هستم");
        type_of_ownership.add("ساکن و مالک هستم");

        ArrayAdapter<String> dataAdapter4 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, type_of_ownership);
        spinner4.setAdapter(dataAdapter4);

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, number_of_units);
        spinner3.setAdapter(dataAdapter3);


        return view;
    }
}