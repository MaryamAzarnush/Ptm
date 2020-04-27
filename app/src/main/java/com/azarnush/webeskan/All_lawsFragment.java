package com.azarnush.webeskan;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class All_lawsFragment extends Fragment implements View.OnClickListener {
    Button btn_LawOfPossession, btn_Regulations, btn_question_and_answer, btn_votes, btn_Rules_webeskan;
    public static boolean connected;
    View root;
    Context context;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_all_laws, container, false);
        context = root.getContext();
        findViews();
        HomeActivity.toolbar.setTitle("قوانین و مقررات");
        animation();

        btn_LawOfPossession.setOnClickListener(this);
        btn_Regulations.setOnClickListener(this);
        btn_question_and_answer.setOnClickListener(this);
        btn_votes.setOnClickListener(this);
        btn_Rules_webeskan.setOnClickListener(this);
        isConnected();


        if (Prefs.with(context).isFirstLoad() && (connected == false)) {

            Toast.makeText(context, "لطفا اینترنت را فقط برای بار اول روشن کنید.", Toast.LENGTH_LONG).show();
            //getActivity().finish();
        }
        if (connected == true) {
            Prefs.with(context).firstLoadIsDone();
        }

        return root;
    }
    private void animation(){
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_bottom);
        relativeLayout.startAnimation(animation);
    }

    public boolean isConnected() {

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();

        } catch (Exception e) {
            // Toast.makeText(context, e.toString(), Toast.LENGTH_LONG);

        }
        return connected;
    }

    public void findViews() {
        btn_LawOfPossession = root.findViewById(R.id.btn_LawOfPossession);
        btn_Regulations = root.findViewById(R.id.btn_Regulations);
        btn_question_and_answer = root.findViewById(R.id.btn_question_and_answer);
        btn_votes = root.findViewById(R.id.btn_votes);
        btn_Rules_webeskan = root.findViewById(R.id.btn_Rules_webeskan);
        relativeLayout= root.findViewById(R.id.container_button);

    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.btn_LawOfPossession:

                fragment = new Law_of_PossessionFragment();

                break;
            case R.id.btn_Regulations:
                fragment = new RegulationsFragment();
                break;
            case R.id.btn_question_and_answer:
                fragment = new Question_and_answerFragment();
                break;
            case R.id.btn_votes:
                fragment = new VotesFragment();
                break;
            case R.id.btn_Rules_webeskan:
                fragment = new Rules_webeskanFragment();
                break;

        }

        HomeActivity.fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, fragment)
                .addToBackStack(null).commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.toolbar.setTitle("قوانین و مقررات");
        HomeActivity.imageShare.setVisibility(View.INVISIBLE);

    }
}

