package com.azarnush.webeskan;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {
    View root;
    TextView txt_firstNameAndLastName, txt_mobile;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_profile, container, false);

        txt_firstNameAndLastName = root.findViewById(R.id.txt_firstNameAndLastName);
        txt_mobile = root.findViewById(R.id.txt_mobile);
        //قرار شد تو api login بده
//        txt_firstNameAndLastName.setText(Resident_informationFragment.shPref.getString("FirstName" ,"") +" "+
//                Resident_informationFragment.shPref.getString("LastName", ""));


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Resident_panelActivity.toolbar.setTitle("تنطیمات پروفایل");
    }
}
