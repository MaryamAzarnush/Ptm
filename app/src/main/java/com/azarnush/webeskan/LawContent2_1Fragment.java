package com.azarnush.webeskan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.azarnush.webeskan.models.LawInfo2;
import com.azarnush.webeskan.models.LawInfo3;

public class LawContent2_1Fragment extends Fragment {
    TextView txt_content;
    TextView txt_law_title;
    TextView txt_law_taq;

    String my_text = "";
    View root;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_law_content, container, false);
        context = root.getContext();

        HomeActivity.imageShare.setVisibility(View.VISIBLE);

        txt_content = root.findViewById(R.id.txt_content);
        txt_law_title = root.findViewById(R.id.txt_law_title);
        txt_law_taq = root.findViewById(R.id.txt_law_taq);

        LawInfo3 lawInfo33 = RegulationsFragment.lawinfos3.get(0);
        txt_law_title.setText(lawInfo33.getLawTitle());
        txt_content.setText(Html.fromHtml(lawInfo33.getLawContent()));
        my_text = lawInfo33.getLawTitle() + " \n\n" + Html.fromHtml(lawInfo33.getLawContent());
        if (lawInfo33.getLawTag().equalsIgnoreCase("null")) {
            txt_law_taq.setText("");
        } else txt_law_taq.setText(lawInfo33.getLawTag());

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
