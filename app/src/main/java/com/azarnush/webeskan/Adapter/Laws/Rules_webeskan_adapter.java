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
import com.azarnush.webeskan.Laws.LawContent6Fragment;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo6;

import java.util.List;

public class Rules_webeskan_adapter extends RecyclerView.Adapter<Rules_webeskan_adapter.Rules_webeskan_ViewHolder> {
    private List<LawInfo6> lawinfos6;
    private Context mContext;


    public Rules_webeskan_adapter(List<LawInfo6> lawinfos6, Context mContext) {
        this.lawinfos6 = lawinfos6;
        this.mContext = mContext;
    }

    @Override
    public Rules_webeskan_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reguration_list_row, parent, false);
        return new Rules_webeskan_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Rules_webeskan_ViewHolder holder, final int position) {
        LawInfo6 lawinfo3 = lawinfos6.get(position);

        holder.txt_title.setText(lawinfo3.getLawTitle());
        holder.txt_lawsummary.setText(lawinfo3.getLawSummary());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (position) {
                    case 0:
                        Fragment fragment = new LawContent6Fragment();
                        HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).commit();
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        return lawinfos6.size();
    }

    public class Rules_webeskan_ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView txt_title;
        private TextView txt_lawsummary;

        private Rules_webeskan_ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.crd_view_lawinfo2);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_lawsummary = itemView.findViewById(R.id.txt_lawsummary);

        }
    }
}
