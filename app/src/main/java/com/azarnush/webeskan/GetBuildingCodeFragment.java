package com.azarnush.webeskan;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GetBuildingCodeFragment extends DialogFragment {
    View view;
    Button btn_get_units;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_get_building_code, container, false);
        btn_get_units = view.findViewById(R.id.btn_get_units);
        btn_get_units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewunitFragment fragment = new NewunitFragment();
                fragment.show(Resident_panelActivity.fragmentManager, "dialog2");
            }
        });


        return view;
    }
}