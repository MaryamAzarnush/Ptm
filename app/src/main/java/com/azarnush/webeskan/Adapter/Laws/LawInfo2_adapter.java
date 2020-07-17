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
import com.azarnush.webeskan.Laws.LawContent1Fragment;
import com.azarnush.webeskan.Laws.LawContent2_2Fragment;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo2;

import java.util.List;

public class LawInfo2_adapter extends RecyclerView.Adapter<LawInfo2_adapter.LawInfo2ViewHolder> {
    private List<LawInfo2> lawinfos2;
    private Context mContext;
    public static String number_page = "";
    Fragment fragment;

    public LawInfo2_adapter(List<LawInfo2> lawinfos2, Context mContext) {
        this.lawinfos2 = lawinfos2;
        this.mContext = mContext;

    }

    @Override
    public LawInfo2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reguration_list_row, parent, false);
        return new LawInfo2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LawInfo2ViewHolder holder, final int position) {
        LawInfo2 lawinfo2 = lawinfos2.get(position);

        holder.txt_title.setText(lawinfo2.getLawTitle());
        holder.txt_lawsummary.setText(lawinfo2.getLawSummary());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (position) {
                    case 0:

                        fragment = new LawContent1Fragment();
                        HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
                        break;
                    case 1:

                        fragment = new LawContent2_2Fragment();
                        HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
                        break;


                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return lawinfos2.size();
    }

    public class LawInfo2ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        private TextView txt_title;
        private TextView txt_lawsummary;


        private LawInfo2ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.crd_view_lawinfo2);

            txt_title = itemView.findViewById(R.id.txt_title);
            txt_lawsummary = itemView.findViewById(R.id.txt_lawsummary);
        }
    }
}