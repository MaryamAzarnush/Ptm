package com.azarnush.webeskan.Adapter.ResidentPanel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azarnush.webeskan.R;
import com.azarnush.webeskan.Resident_boardFragment;
import com.azarnush.webeskan.models.ResidentPanel.Receive;

import java.util.List;

public class ReceiveAdapter extends RecyclerView.Adapter<ReceiveAdapter.ReceiveViewHolder> {
    List<Receive> receives;

    public ReceiveAdapter(List<Receive> receives) {
        this.receives = receives;
    }

    @NonNull
    @Override
    public ReceiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_receives, parent, false);
        return new ReceiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveViewHolder holder, int position) {
        if (receives.size() > 0) {
            Receive receive = receives.get(position);
            holder.txt_description.setText(receive.getReceiveDescription());
            holder.txt_amount.setText(String.valueOf(Resident_boardFragment.nf.format(receive.getReceiveAmount())));
            holder.btn_receive_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return receives.size();
    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder {
        TextView txt_description, txt_amount;
        Button btn_receive_details;

        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_amount = itemView.findViewById(R.id.txt_amount);
            btn_receive_details = itemView.findViewById(R.id.btn_receive_details);

        }
    }
}
