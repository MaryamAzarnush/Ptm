package com.azarnush.webeskan.Adapter.Laws;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo;


import java.util.List;

public class LawInfo_adapter extends RecyclerView.Adapter<LawInfo_adapter.LawInfoViewHolder> {
    private List<LawInfo> lawinfos;
    private Context mcontext;


    public LawInfo_adapter(List<LawInfo> lawinfos, Context mcontext) {
        this.lawinfos = lawinfos;
        this.mcontext = mcontext;
    }

    @Override
    public LawInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lawinfo_list_row, parent, false);
        return new LawInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LawInfoViewHolder holder, int position) {
        LawInfo lawinfo = lawinfos.get(position);

        holder.txt_title.setText(lawinfo.getLawTitle());
        holder.txt_lawsummary.setText(lawinfo.getLawSummary());

    }

    @Override
    public int getItemCount() {

        return lawinfos.size();
    }

    public class LawInfoViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title;
        private TextView txt_lawsummary;


        private LawInfoViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_lawsummary = itemView.findViewById(R.id.txt_lawsummary);


        }
    }
}