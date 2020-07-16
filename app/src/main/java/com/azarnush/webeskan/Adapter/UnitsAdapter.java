package com.azarnush.webeskan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.azarnush.webeskan.R;
import com.azarnush.webeskan.Resident_boardFragment2;
import com.azarnush.webeskan.Resident_panelActivity;
import com.azarnush.webeskan.models.Unit;

import java.util.List;

public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.UnitViewHolder> {

    List<Unit> units;
    public static Integer residenceRefId;
    public static String building_NameAndUnit;

    public UnitsAdapter(List<Unit> units) {
        this.units = units;
    }

    @NonNull
    @Override
    public UnitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_units, parent, false);
        return new UnitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UnitViewHolder holder, final int position) {
        final Unit unit = units.get(position);

        holder.txt_auto_number.setText(unit.getAuto_number());
        holder.txt_buildingTitle.setText(unit.getBuildingTitle());
        holder.txt_unitTitle.setText(unit.getUnitTitle());

        holder.container_units.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                residenceRefId = units.get(position).getResidenceRefId();
                building_NameAndUnit = unit.getBuildingTitle() + " " + unit.getUnitTitle();
                Fragment fragment = new Resident_boardFragment2();
                Resident_panelActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment_resident, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public class UnitViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_auto_number;
        public TextView txt_buildingTitle;
        public TextView txt_unitTitle;
        public LinearLayout container_units;

        public UnitViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_auto_number = itemView.findViewById(R.id.txt_auto_number);
            txt_buildingTitle = itemView.findViewById(R.id.txt_buildingTitle);
            txt_unitTitle = itemView.findViewById(R.id.txt_unitTitle);
            container_units = itemView.findViewById(R.id.container_units);
        }
    }
}
