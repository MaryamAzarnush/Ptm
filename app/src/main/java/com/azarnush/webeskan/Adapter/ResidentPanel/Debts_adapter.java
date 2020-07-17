package com.azarnush.webeskan.Adapter.ResidentPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.ResidentPanel.Debt;

import java.util.List;

public class Debts_adapter extends RecyclerView.Adapter<Debts_adapter.DebtViewHolder> {
    List<Debt> debtsList;

    public Debts_adapter(List<Debt> debtsList) {
        this.debtsList = debtsList;
    }

    @NonNull
    @Override
    public DebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_debts, parent, false);
        return new DebtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtViewHolder holder, int position) {
        Debt debt = debtsList.get(position);

        holder.checkBox.setActivated(debt.isCheckDebt());
        holder.debtTitle.setText(debt.getDebtTitle());
        holder.payableDebtAmount.setText(String.valueOf(debt.getPayableDebtAmount()));
        holder.debtDate.setText(String.valueOf(debt.getDebtDate()));

    }

    @Override
    public int getItemCount() {
        return debtsList.size();
    }

    public class DebtViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView debtTitle, payableDebtAmount, debtDate;

        public DebtViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            debtTitle = itemView.findViewById(R.id.debtTitle);
            payableDebtAmount = itemView.findViewById(R.id.payableDebtAmount);
            debtDate = itemView.findViewById(R.id.debtDate);

        }
    }
}
