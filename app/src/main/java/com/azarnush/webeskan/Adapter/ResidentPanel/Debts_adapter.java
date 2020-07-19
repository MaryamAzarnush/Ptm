package com.azarnush.webeskan.Adapter.ResidentPanel;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azarnush.webeskan.R;
import com.azarnush.webeskan.Resident_boardFragment;
import com.azarnush.webeskan.models.ResidentPanel.Debt;
import java.util.List;

public class Debts_adapter extends RecyclerView.Adapter<Debts_adapter.DebtViewHolder> {
    List<Debt> debtsList;
    Double sum_selected_debts = 0.0;

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
    public void onBindViewHolder(@NonNull final DebtViewHolder holder, int position) {
        final Debt debt = debtsList.get(position);

        holder.checkBox_debt.setChecked(debt.isCheckDebt());
        holder.debtTitle.setText(debt.getDebtTitle());
        holder.payableDebtAmount.setText(String.valueOf(debt.getPayableDebtAmount()));
        holder.debtDate.setText(String.valueOf(debt.getDebtDate()));
        holder.checkBox_debt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    debt.setCheckDebt(true);
                    sum_selected_debts += debt.getPayableDebtAmount();
                } else {
                    debt.setCheckDebt(false);
                    sum_selected_debts -= debt.getPayableDebtAmount();
                }
                Resident_boardFragment.txt_sum.setText(String.valueOf(sum_selected_debts));
                int number = 0;
                for (int i = 0; i < debtsList.size(); i++) {
                    if (debtsList.get(i).isCheckDebt()) {
                        number++;
                        Log.i("ptm", number + "");
                    }
                }
                if (number == debtsList.size()) {
                    Resident_boardFragment.ch_all.setChecked(true);
                }
                if (number == 0) {
                    Resident_boardFragment.ch_all.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return debtsList.size();
    }

    public class DebtViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox_debt;
        TextView debtTitle, payableDebtAmount, debtDate;

        public DebtViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox_debt = itemView.findViewById(R.id.checkBox_debt);
            debtTitle = itemView.findViewById(R.id.debtTitle);
            payableDebtAmount = itemView.findViewById(R.id.payableDebtAmount);
            debtDate = itemView.findViewById(R.id.debtDate);

        }
    }
}
