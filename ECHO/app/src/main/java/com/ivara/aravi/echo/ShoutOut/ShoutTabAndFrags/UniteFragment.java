package com.ivara.aravi.echo.ShoutOut.ShoutTabAndFrags;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ivara.aravi.echo.R;

/**
 * Created by Aravi on 27-08-2017.
 */

public class UniteFragment extends Fragment {

    private Context context;

    public UniteFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_unite, container, false);

        RecyclerView rv = (RecyclerView)rootView.findViewById(R.id.rv_recyclerview_tab1);
        rv.setHasFixedSize(true);
        UniteAdapter UA = new UniteAdapter(context); //new String[] {"Frag One","Frag Two","Frag Three","Frag Four", "Frag Five" , "Frag Six" , "Frag Seven"}
        rv.setAdapter(UA);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return rootView;
    }
}
