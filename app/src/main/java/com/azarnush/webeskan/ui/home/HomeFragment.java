package com.azarnush.webeskan.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.azarnush.webeskan.Laws.All_lawsFragment;
import com.azarnush.webeskan.Get_number_residentFragment;
import com.azarnush.webeskan.HomeActivity;
import com.azarnush.webeskan.Manager_loginFragment;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.Resident_panelActivity;
import com.azarnush.webeskan.WebLogFragment;

public class HomeFragment extends Fragment {


    private HomeViewModel homeViewModel;

    Button btn_laws, btn_weblog, btn_residents_login, btn_managers_login;

    Context context_home;
    public static SharedPreferences homePref;
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context_home = root.getContext();

        btn_laws = root.findViewById(R.id.btn_laws);
        btn_weblog = root.findViewById(R.id.btn_weblog);
        btn_residents_login = root.findViewById(R.id.btn_Residents_login);
        btn_managers_login = root.findViewById(R.id.btn_managers_login);
        homePref = getActivity().getSharedPreferences("my pref", Context.MODE_PRIVATE);

        if (homePref.getBoolean("is login", false)) {
            HomeActivity.navigationView.getMenu().findItem(R.id.nav_exit_Account).setVisible(true);
        }

        btn_laws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new All_lawsFragment();
                HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();

            }
        });

        btn_weblog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new WebLogFragment();
                HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });

        btn_residents_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (homePref.getBoolean("is register", false) && homePref.getBoolean("is login", false)) {
                   // Toast.makeText(getContext(), homePref.getBoolean("is register", false) + "", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(), Resident_panelActivity.class));
                    getActivity().finish();
                } else {
                    Fragment fragment = new Get_number_residentFragment();
                    HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                            .addToBackStack(null).commit();
                }
            }
        });

        btn_managers_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Manager_loginFragment();
                HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                        .addToBackStack(null).commit();
            }
        });
        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);
        HomeActivity.toolbar.setTitle("صفحه اصلی");

    }

}