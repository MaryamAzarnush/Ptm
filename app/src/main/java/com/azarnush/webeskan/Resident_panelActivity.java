package com.azarnush.webeskan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.azarnush.webeskan.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Resident_panelActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView textView_name;
    TextView textView_number;
   public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_panel);
         toolbar = findViewById(R.id.toolbar_resident);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_resident);
        navigationView = findViewById(R.id.nav_view_resident);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home_resident, R.id.nav_home, R.id.nav_settings, R.id.nav_exit_resident, R.id.nav_exit_Account_resident)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_resident);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        View header = navigationView.getHeaderView(0);
        textView_number = (TextView) header.findViewById(R.id.textView_number);
        textView_number.setText(getSharedPreferences("MyPref", Context.MODE_PRIVATE).getString("Mobile", "Mobile"));

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                switch (destination.getId()) {
                    case R.id.nav_home:
                        startActivity(new Intent(Resident_panelActivity.this, HomeActivity.class));
                        finish();
                        break;
                    case R.id.nav_exit_resident:
                        finish();
                        break;
                    case R.id.nav_exit_Account_resident:
                        Toast.makeText(getApplicationContext(), "log out", Toast.LENGTH_SHORT).show();
                        HomeFragment.homePref.edit().putBoolean("is login", false).apply();
                        navigationView.getMenu().findItem(R.id.nav_exit_Account_resident).setVisible(false);
                        startActivity(new Intent(Resident_panelActivity.this, HomeActivity.class));
                        finish();
                        break;

                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_resident);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Resident_panelActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.drawable.logo);
            builder.setMessage("آیا قصد خروج از برنامه را دارید؟")
                    .setCancelable(false)
                    .setPositiveButton("", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Toast.makeText(getApplicationContext(), "finish", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    })
                    .setNegativeButton("", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.setPositiveButtonIcon(getResources().getDrawable(R.drawable.ic_done))
                    .setNegativeButtonIcon(getResources().getDrawable(R.drawable.ic_cancel));

            AlertDialog alert = builder.create();
            alert.show();


        }
    }
}