package com.azarnush.webeskan.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Unit;

import java.util.List;

public class UnitsAdapter extends RecyclerView.Adapter<UnitsAdapter.UnitViewHolder> {

    List<Unit> units;

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
    public void onBindViewHolder(@NonNull UnitViewHolder holder, int position) {
        Unit unit = units.get(position);

        holder.txt_auto_number.setText(unit.getAuto_number());
        holder.txt_buildingTitle.setText(unit.getBuildingTitle());
        holder.txt_unitTitle.setText(unit.getUnitTitle());
    }

    @Override
    public int getItemCount() {
        return units.size();
    }

    public class UnitViewHolder extends RecyclerView.ViewHolder{
        public TextView txt_auto_number;
        public TextView txt_buildingTitle;
        public TextView txt_unitTitle;

        public UnitViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_auto_number = itemView.findViewById(R.id.txt_auto_number);
            txt_buildingTitle = itemView.findViewById(R.id.txt_buildingTitle);
            txt_unitTitle = itemView.findViewById(R.id.txt_unitTitle);
        }
    }
}
