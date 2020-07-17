package com.azarnush.webeskan.Laws;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.azarnush.webeskan.HomeActivity;
import com.azarnush.webeskan.R;
import com.azarnush.webeskan.models.Laws.LawInfo5;

public class LawContent4Fragment extends Fragment {

    TextView txt_content;
    TextView txt_law_title;
    TextView txt_law_taq;

    String my_text = "";

    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_law_content, container, false);

        context = root.getContext();

        txt_content = root.findViewById(R.id.txt_content);
        txt_law_title = root.findViewById(R.id.txt_law_title);
        txt_law_taq = root.findViewById(R.id.txt_law_taq);


        LawInfo5 lawInfo55 = VotesFragment.lawinfos5.get(0);
        txt_law_title.setText(lawInfo55.getLawTitle());
        txt_content.setText(Html.fromHtml(lawInfo55.getLawContent()));
        my_text = lawInfo55.getLawTitle() + " \n\n" + Html.fromHtml(lawInfo55.getLawContent());
        if (lawInfo55.getLawTag().equalsIgnoreCase("null")) {
            txt_law_taq.setText("");
        } else txt_law_taq.setText(lawInfo55.getLawTag());
        HomeActivity.imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, my_text);
                startActivity(Intent.createChooser(intent, "اشتراک گذاری متن با "));
            }
        });


        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.imageShare.setVisibility(View.VISIBLE);
        HomeActivity.toolbar.setTitle("قوانین و مقررات");
    }
}
