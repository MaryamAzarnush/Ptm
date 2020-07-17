package com.azarnush.webeskan.Adapter.Laws;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.azarnush.webeskan.HomeActivity;
import com.azarnush.webeskan.Laws.LawContent4Fragment;
import com.azarnush.webeskan.Laws.LawContent5Fragment;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo5;


import java.util.ArrayList;
import java.util.List;

public class Votes_adapter extends RecyclerView.Adapter<Votes_adapter.Votes_ViewHolder> {

    private List<LawInfo5> lawinfos5;
    private Context mContext;
    Fragment fragment;

    public Votes_adapter(ArrayList<LawInfo5> lawinfos5, Context mContext) {
        this.lawinfos5 = lawinfos5;
        this.mContext = mContext;
    }

    @Override
    public Votes_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reguration_list_row, parent, false);
        return new Votes_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Votes_ViewHolder holder,
                                 final int position) {
        LawInfo5 lawinfo5 = lawinfos5.get(position);

        holder.txt_title.setText(lawinfo5.getLawTitle());
        holder.txt_lawsummary.setText(lawinfo5.getLawSummary());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (position) {
                    case 0:
                        fragment = new LawContent4Fragment();
                        HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        break;
                    case 1:
                        fragment = new LawContent5Fragment();
                        HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        break;

                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return lawinfos5.size();
    }

    public class Votes_ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView txt_title;
        private TextView txt_lawsummary;

        private Votes_ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.crd_view_lawinfo2);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_lawsummary = itemView.findViewById(R.id.txt_lawsummary);

        }
    }
}
